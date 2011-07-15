// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

/**
 * 
 */
package cern.accsoft.steering.jmad.tools.response;

import java.util.List;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.JMadElementType;
import cern.accsoft.steering.jmad.domain.elem.impl.Corrector;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.filter.NameFilter;
import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultImpl;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * This implementation of {@link ResponseMatrixTool} calculates the response matrix using the exact kick strengths given
 * in the request and calculates the response matrix by the use of two trajectories returned by the madx-model. It thus
 * includes all (even nonlinear) effects and coupling.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class FullResponseMatrixTool implements ResponseMatrixTool {

    /**
     * below this value we treat the kick as zero and leave the matrix values also at zero.
     */
    private static final double KICK_ZERO_LIMIT = 1e-10;

    @Override
    public Matrix calcResponseMatrix(JMadModel model, ResponseRequest request) throws JMadModelException {

        List<String> monitorNames = request.getMonitorNames();
        List<String> correctorNames = request.getCorrectorNames();
        List<JMadPlane> monitorPlanes = request.getMonitorPlanes();
        List<JMadPlane> correctorPlanes = request.getCorrectorPlanes();
        List<Double> strengthValues = request.getStrengthValues();

        Matrix matrix = new Matrix(monitorNames.size(), correctorNames.size());

        for (int i = 0; i < correctorNames.size(); i++) {
            String correctorName = correctorNames.get(i);
            JMadPlane correctorPlane = correctorPlanes.get(i);
            double strengthValue = strengthValues.get(i);

            Element element = model.getActiveRange().getElement(correctorName);
            if (element == null) {
                throw new JMadModelException("Could not find element with name '" + correctorName
                        + "' in active range.");
            }
            if (!JMadElementType.CORRECTOR.isTypeOf(element)) {
                throw new JMadModelException("Element '" + element.getName()
                        + "' is not a corrector! Cannot calc response for this element!");
            }
            Corrector corrector = (Corrector) element;

            /*
             * calc plus and minus response
             */

            TfsResultImpl minus = calcResponse(model, corrector, correctorPlane, -strengthValue, monitorNames,
                    request.getMonitorRegexps());
            TfsResultImpl plus = calcResponse(model, corrector, correctorPlane, strengthValue, monitorNames,
                    request.getMonitorRegexps());
            Double deltaKick = 2 * strengthValue;

            if (Math.abs(deltaKick) < KICK_ZERO_LIMIT) {
                continue;
            }

            List<Double> plusXData = plus.getDoubleData(MadxTwissVariable.X);
            List<Double> plusYData = plus.getDoubleData(MadxTwissVariable.Y);
            List<Double> minusXData = minus.getDoubleData(MadxTwissVariable.X);
            List<Double> minusYData = minus.getDoubleData(MadxTwissVariable.Y);

            for (int j = 0; j < monitorNames.size(); j++) {
                String monitorName = monitorNames.get(j);
                JMadPlane monitorPlane = monitorPlanes.get(j);

                Integer plusIndex = plus.getElementIndex(monitorName);
                if (plusIndex == null) {
                    throw new JMadModelException("No Data for monitor '" + monitorName + "' in plus-Result.");
                }
                Integer minusIndex = minus.getElementIndex(monitorName);
                if (minusIndex == null) {
                    throw new JMadModelException("No Data for monitor '" + monitorName + "' in minus-Result.");
                }

                Double deltaPos = null;
                if (JMadPlane.H.equals(monitorPlane)) {
                    deltaPos = plusXData.get(plusIndex) - minusXData.get(minusIndex);
                } else if (JMadPlane.V.equals(monitorPlane)) {
                    deltaPos = plusYData.get(plusIndex) - minusYData.get(minusIndex);
                } else {
                    throw new JMadModelException("Unable to handle plane '" + monitorPlane + "'");
                }

                double value = deltaPos / deltaKick;

                /*
                 * inverting if it relates to a corrector that should be inverted by definition
                 */
                for (NameFilter filter : model.getActiveRange().getCorrectorInvertFilters()) {
                    if (filter.isConcerned(correctorName, correctorPlane)) {
                        value *= -1;
                        break;
                    }
                }

                /*
                 * inverting if it relates to a monitor that should be inverted by definition
                 */
                for (NameFilter filter : model.getActiveRange().getMonitorInvertFilters()) {
                    if (filter.isConcerned(monitorName, monitorPlane)) {
                        value *= -1;
                        break;
                    }
                }

                matrix.set(j, i, value);
            }
        }
        return matrix;
    }

    /**
     * calcs the response for one corrector
     * 
     * @param model the model from which to calc the response
     * @param corrector the corrector for which to calc the response
     * @param plane the plane in where to kick
     * @param kick the value for the kick
     * @param monitorNames the monitorNames to be included in the result
     * @param monitorRegexps regexpressions which represent all monitors (using this makes the twiss faster)
     * @return the result of the twiss
     * @throws JMadModelException if something goes wrong
     */
    private TfsResultImpl calcResponse(JMadModel model, Corrector corrector, JMadPlane plane, double kick,
            List<String> monitorNames, List<String> monitorRegexps) throws JMadModelException {

        /* store the actual kick for setting back later */
        double oldKick = corrector.getKick(plane);

        corrector.setKick(plane, oldKick + kick);

        TfsResultRequestImpl resultRequest = new TfsResultRequestImpl();
        if (monitorRegexps.isEmpty()) {
            /*
             * if none are defined, then we add just the monitorNames, but this seems to be slower for some reason.
             */
            for (String monitorName : monitorNames) {
                resultRequest.addElementFilter(monitorName);
            }
        } else {
            /*
             * if element-names for the response were defined for the range, then we use them
             */
            for (String regexp : monitorRegexps) {
                resultRequest.addElementFilter(regexp);
            }
        }
        resultRequest.addVariable(MadxTwissVariable.NAME);
        resultRequest.addVariable(MadxTwissVariable.X);
        resultRequest.addVariable(MadxTwissVariable.Y);
        resultRequest.addVariable(MadxTwissVariable.KEYWORD);

        TfsResultImpl tfsResult;
        try {
            Result result = model.twiss(resultRequest);
            if (ResultType.TFS_RESULT != result.getResultType()) {
                throw new JMadModelException("Twiss returned wrong type of result!");
            }
            tfsResult = (TfsResultImpl) result;
        } finally {
            /* reset strength to old Value */
            corrector.setKick(plane, oldKick);
        }
        return tfsResult;
    }

}
