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

package cern.accsoft.steering.jmad.tools.interpolate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.optics.OpticPointImpl;
import cern.accsoft.steering.jmad.domain.orbit.Orbit;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;

/**
 * This class does the actual interpolation of the orbit in one plane for one segment defined by two adjacent monitors.
 * 
 * @author muellerg
 */
public class SimpleOrbitSegmentCalculator implements OrbitSegmentCalculator {
    private static final Logger LOGGER = Logger.getLogger(SimpleOrbitSegmentCalculator.class);

    /** the plane this orbit segment calculator works on */
    private JMadPlane plane;

    /** the monitor at the beginning of this segment */
    private Element startMonitor;
    /** the monitor at the end of the segment */
    private Element endMonitor;

    /** the transfer matrix between the start and end monitor */
    private Matrix monitorTransferMatrix = null;

    /** the transfer matrix mapping from the start monitor to all the elements of the segment */
    private Map<Element, Matrix> elementMatrices;

    /** the list of elements in the segment, including the monitors (first/last) */
    private LinkedList<Element> segmentElements = new LinkedList<Element>();

    /** flag to determine if segment contains start and end of circular machine */
    private boolean isCycleStartSegment = false;

    /** the maximum mu in the segment */
    private double maxMu;

    public SimpleOrbitSegmentCalculator(JMadPlane plane) {
        this.plane = plane;
        this.elementMatrices = new HashMap<Element, Matrix>();
    }

    @Override
    public void setStartSegmentMonitor(Element element) {
        this.startMonitor = element;
        this.segmentElements.addFirst(element);
    }

    @Override
    public void setEndSegmentMonitor(Element element) {
        this.endMonitor = element;
        this.segmentElements.addLast(element);
    }

    @Override
    public void addElementToCalculate(Element element) {
        this.segmentElements.add(element);
    }

    @Override
    public boolean update(Optic optic) {
        if (this.isCycleStartSegment()) {
            this.updateMuMax(optic);
        }

        if (!this.updateMonitorTransferMatrix(optic)) {
            return false;
        }

        OpticPoint from = optic.getPoint(this.startMonitor);
        for (Element element : this.segmentElements) {
            if (element.equals(startMonitor) || element.equals(endMonitor)) {
                continue;
            }

            OpticPoint to = optic.getPoint(element);
            if (to == null) {
                LOGGER.error("Could not update transfer matrix in " + this.getName() + " no optic data for element ["
                        + element.getName() + "] in plane [" + this.getPlane() + "].");
                return false;
            }

            this.elementMatrices.put(element, this.calculateTransferMatrix(from, to));
        }

        return true;
    }

    private void updateMuMax(Optic optic) {
        double maxMu = Double.MIN_VALUE;
        for (Element element : this.segmentElements) {
            double mu = optic.getPoint(element).getValue(JMadTwissVariable.MU, this.getPlane());
            if (mu > maxMu) {
                maxMu = mu;
            }
        }

        this.setMaxMu(maxMu);
    }

    private void setMaxMu(double maxMu) {
        this.maxMu = maxMu;
    }

    private double getMaxMu() {
        return this.maxMu;
    }

    private Matrix calculateTransferMatrix(OpticPoint from, OpticPoint to) {
        if (!this.isCycleStartSegment()) {
            return TransferMatrixCalculator.calculate(getPlane(), from, to);
        }

        double muFrom = from.getValue(JMadTwissVariable.MU, this.getPlane());
        double muTo = to.getValue(JMadTwissVariable.MU, this.getPlane());

        if (muTo < muFrom) {
            muTo = muTo + (this.getMaxMu() - muFrom);
            muFrom = 0.0;
            return TransferMatrixCalculator.calculate(getPlane(), this.adaptOpticPoint(from, muFrom), this
                    .adaptOpticPoint(to, muTo));
        } else {
            return TransferMatrixCalculator.calculate(getPlane(), from, to);
        }
    }

    private OpticPoint adaptOpticPoint(OpticPoint point, double newMu) {
        OpticPointImpl newPoint = new OpticPointImpl(point.getName());
        for (JMadTwissVariable variable : TransferMatrixCalculator.REQUIRED_VARIABLES) {
            newPoint.setValue(variable.getMadxTwissVariable(getPlane()), point.getValue(variable, this.getPlane()));
        }

        newPoint.setValue(JMadTwissVariable.MU.getMadxTwissVariable(getPlane()), newMu);
        return newPoint;
    }

    private boolean updateMonitorTransferMatrix(Optic optic) {

        OpticPoint from = optic.getPoint(this.startMonitor);
        OpticPoint to = optic.getPoint(endMonitor);

        if (from == null || to == null) {
            LOGGER.error("Could not update monitor transfer matrix in " + this.getName()
                    + " no optic data for monitors.");
            this.monitorTransferMatrix = null;
            return false;
        }

        this.monitorTransferMatrix = this.calculateTransferMatrix(from, to);
        return true;
    }

    @Override
    public Map<Element, Double> calculate(Orbit orbit) {
        Map<Element, Double> valueMapping = new HashMap<Element, Double>();
        if (this.monitorTransferMatrix == null) {
            LOGGER.error("Segment orbit interpolation calculator not initialized for " + this.getName());
            return valueMapping;
        }

        double pos_start = this.getMonitorPosition(this.startMonitor, orbit);
        double pos_end = this.getMonitorPosition(this.endMonitor, orbit);

        double c_seg = this.monitorTransferMatrix.get(0, 0);
        double s_seg = this.monitorTransferMatrix.get(0, 1);

        double factor = (pos_end - (c_seg * pos_start)) / s_seg;

        for (Element element : this.segmentElements) {
            /* for the monitors we just copy the reading */
            if (element.equals(startMonitor)) {
                valueMapping.put(element, pos_start);
                continue;
            }
            if (element.equals(endMonitor)) {
                valueMapping.put(element, pos_end);
                continue;
            }

            /* do the interpolation */
            Matrix elementMatrix = this.elementMatrices.get(element);
            double c_elem = elementMatrix.get(0, 0);
            double s_elem = elementMatrix.get(0, 1);
            double value = c_elem * pos_start + s_elem * factor;

            valueMapping.put(element, value);
        }

        return valueMapping;
    }

    private double getMonitorPosition(Element monitor, Orbit orbit) {
        int index = orbit.getMonitorIndex(monitor.getName());
        return orbit.getValues(this.getPlane()).get(index);
    }

    @Override
    public String getName() {
        return "segment [" + this.startMonitor.getName() + "-->" + this.endMonitor.getName() + "] for plane ["
                + this.getPlane() + "]";
    }

    @Override
    public JMadPlane getPlane() {
        return this.plane;
    }

    @Override
    public void setIsCycleStartSegment(boolean isCycleStartSegment) {
        this.isCycleStartSegment = isCycleStartSegment;
    }

    private boolean isCycleStartSegment() {
        return isCycleStartSegment;
    }
}
