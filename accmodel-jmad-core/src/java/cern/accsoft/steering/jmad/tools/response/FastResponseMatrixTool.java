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
import cern.accsoft.steering.jmad.domain.beam.Beam.Direction;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.filter.NameFilter;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * @author kfuchsbe
 */
public class FastResponseMatrixTool implements ResponseMatrixTool {

    @Override
    public Matrix calcResponseMatrix(JMadModel model, ResponseRequest request) throws JMadModelException {
        List<String> monitorNames = request.getMonitorNames();
        List<String> correctorNames = request.getCorrectorNames();
        List<JMadPlane> monitorPlanes = request.getMonitorPlanes();
        List<JMadPlane> correctorPlanes = request.getCorrectorPlanes();

        Matrix matrix = new Matrix(monitorNames.size(), correctorNames.size());

        for (int i = 0; i < monitorNames.size(); i++) {
            String monitorName = monitorNames.get(i);
            JMadPlane monitorPlane = monitorPlanes.get(i);
            for (int j = 0; j < correctorNames.size(); j++) {
                String strengthElementName = correctorNames.get(j);
                JMadPlane correctorPlane = correctorPlanes.get(j);

                if (!monitorPlane.equals(correctorPlane)) {
                    matrix.set(i, j, 0);
                    continue;
                }

                OpticPoint opticsMonitor = model.getOptics().getPointByName(monitorName);
                OpticPoint opticsCorrector = model.getOptics().getPointByName(strengthElementName);

                if (opticsMonitor == null) {
                    throw new JMadModelException("Could not find Monitor '" + monitorName + "' in model '"
                            + model.getName() + "'. Perhaps you are using the wrong model?");
                }

                if (opticsCorrector == null) {
                    throw new JMadModelException("Could not find Corrector '" + strengthElementName + "' in model '"
                            + model.getName() + "'. Perhaps you are using the wrong model?");
                }

                double betaMonitor;
                double betaCorrector;
                double phaseMonitor;
                double phaseCorrector;

                if (monitorPlane == JMadPlane.H) {
                    betaMonitor = opticsMonitor.getBetx();
                    betaCorrector = opticsCorrector.getBetx();
                    phaseMonitor = opticsMonitor.getMux();
                    phaseCorrector = opticsCorrector.getMux();
                } else {
                    betaMonitor = opticsMonitor.getBety();
                    betaCorrector = opticsCorrector.getBety();
                    phaseMonitor = opticsMonitor.getMuy();
                    phaseCorrector = opticsCorrector.getMuy();
                }

                double value = 0;
                /*
                 * when using beam2 (inverted beam) the phase decreases in beam direction!
                 */
                if (Direction.MINUS.equals(model.getActiveRange().getRangeDefinition().getSequenceDefinition()
                        .getBeam().getDirection())) {
                    phaseMonitor *= -1;
                    phaseCorrector *= -1;
                }

                /*
                 * XXX the following is at the moment only correct for transfer-lines! Not valid for closed orbits!
                 */
                if (phaseMonitor > phaseCorrector) {
                    value = Math.sqrt(betaMonitor * betaCorrector)
                            * Math.sin((phaseMonitor - phaseCorrector) * 2 * Math.PI);
                }

                /*
                 * inverting if it relates to a corrector that should be inverted by definition
                 */
                for (NameFilter filter : model.getActiveRange().getCorrectorInvertFilters()) {
                    if (filter.isConcerned(strengthElementName, correctorPlane)) {
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

                matrix.set(i, j, value);
            }
        }
        return matrix;
    }

}
