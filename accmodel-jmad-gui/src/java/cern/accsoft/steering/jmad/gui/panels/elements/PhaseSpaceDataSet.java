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

package cern.accsoft.steering.jmad.gui.panels.elements;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;
import cern.accsoft.steering.util.gui.dv.ds.ListDataSet;

/**
 * The Dataset for the charts to display the phase space coordinates.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class PhaseSpaceDataSet extends ListDataSet {
    private static final long serialVersionUID = 1L;

    /**
     * if set to <code>true</code> then the coordinates are displayed in normalized phase space coordinates. If it is
     * <code>false</code>, then the real phase space coordinates are displayed.
     */
    private boolean normalized = true;

    /** the plane for which to display the coordinates */
    private JMadPlane plane = JMadPlane.H;

    /** All the optics points which shall be displayed */
    private List<OpticPoint> opticsPoints = new ArrayList<OpticPoint>();

    /**
     * The default constructor which requires the name of the dataset
     * 
     * @param name the name of the dataset
     */
    public PhaseSpaceDataSet(String name, JMadPlane plane) {
        super(name);
        this.plane = plane;
    }

    /**
     * recalculates all the x and y values.
     */
    private void recalc() {
        List<Double> xValues = new ArrayList<Double>();
        List<Double> yValues = new ArrayList<Double>();

        for (OpticPoint opticPoint : this.opticsPoints) {
            double pos = opticPoint.getValue(JMadTwissVariable.POS, this.plane);
            double angle = opticPoint.getValue(JMadTwissVariable.P, this.plane);
            if (isNormalized()) {
                double sqrtBeta = Math.sqrt(opticPoint.getValue(JMadTwissVariable.BETA, this.plane));
                double normalizedPos = pos / sqrtBeta;
                double normalizedAngle = normalizedPos * opticPoint.getValue(JMadTwissVariable.ALFA, this.plane)
                        + sqrtBeta * angle;
                xValues.add(normalizedPos);
                yValues.add(normalizedAngle);
            } else {
                xValues.add(pos);
                yValues.add(angle);
            }
        }

        super.setValues(xValues, yValues);
    }

    /**
     * extracts the names of the elements from the optics points and sets them as labels.
     */
    private void updateLabels() {
        List<String> labels = new ArrayList<String>();
        for (OpticPoint opticPoint : this.opticsPoints) {
            labels.add(opticPoint.getName());
        }
        super.setLabels(labels);
    }

    public synchronized void setOpticsPoints(List<OpticPoint> opticsPoints) {
        this.opticsPoints = opticsPoints;
        updateLabels();
        recalc();
    }

    public void setNormalized(boolean normalized) {
        this.normalized = normalized;
        recalc();
    }

    public boolean isNormalized() {
        return normalized;
    }
}
