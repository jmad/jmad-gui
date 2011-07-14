// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
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
import cern.accsoft.steering.jmad.domain.orbit.Orbit;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

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

            this.elementMatrices.put(element, TransferMatrixCalculator.calculate(this.getPlane(), from, to));
        }

        return true;
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

        this.monitorTransferMatrix = TransferMatrixCalculator.calculate(this.getPlane(), from, to);
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
}
