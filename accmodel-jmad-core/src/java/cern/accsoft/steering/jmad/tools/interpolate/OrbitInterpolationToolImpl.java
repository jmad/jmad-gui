// @formatter:off
/*******************************************************************************
 * This file is part of JMad. Copyright (c) 2008-2011, CERN. All rights reserved. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.tools.interpolate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cern.accsoft.steering.jmad.JMadException;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.orbit.Orbit;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public class OrbitInterpolationToolImpl implements OrbitInterpolationTool {

    /** the calculators doing the actual calculation job */
    private List<OrbitSegmentCalculator> calculators = new ArrayList<OrbitSegmentCalculator>();

    /**
     * Create the internal structure for the interpolation. This method assumes,that the list of elements provided
     * describe a complete machine. A calculator for the given plane is created for each region in the machine that is
     * defined by a starting monitor and a ending monitor. If isCircularMachine is set to <code>true</code> the region
     * from the last monitor in the machine sequence to the first one is treated as a region as well. Otherwise there is
     * no interpolation performed before the first and after the last monitor.
     * 
     * @param plane the {@link JMadPlane} for which to create the interpolation structure
     * @param elements the list of {@link Element}s to create the structure from
     * @param monitors the monitors to use for the interpolations
     * @param isCircularMachine set to <code>true</code> if the machine described by the list of elements is a circular
     *            accelerator
     * @return <code>true</code> if the creation of the structure was successful
     */
    private boolean createStructure(JMadPlane plane, List<Element> elements, Set<Element> monitors,
            boolean isCircularMachine) {
        /* clean up */
        this.clearStructure(plane);

        List<Element> lattice = this.sortElementsByPosition(elements);
        List<Element> elementsBeforeFirstMonitor = new ArrayList<Element>();
        Element firstMonitor = null;
        boolean segmentInsertActive = false;
        OrbitSegmentCalculator calculator = null;
        for (Element element : lattice) {
            if (isCircularMachine && !segmentInsertActive && !monitors.contains(element)) {
                /* not a monitor and we are not in a segment yet */
                /* save all the elements for the 'last' calculator */
                elementsBeforeFirstMonitor.add(element);
                continue;
            }

            if (monitors.contains(element)) {
                if (!segmentInsertActive) {
                    /* this is our first monitor */
                    calculator = this.createNewCalculator(plane, element);
                    if (isCircularMachine) {
                        firstMonitor = element;
                    }
                    segmentInsertActive = true;
                    continue;
                } else {
                    /* the end of the current segment */
                    calculator.setEndSegmentMonitor(element);
                    this.calculators.add(calculator);

                    /* and we start with the next segment */
                    calculator = this.createNewCalculator(plane, element);
                    continue;
                }
            }

            if (segmentInsertActive) {
                /* we are in the segment, so just add all elements to the current calculator */
                calculator.addElementToCalculate(element);
            }
        }

        if (isCircularMachine && calculator != null) {
            /* add the elements before the first monitor to the last segment */
            for (Element element : elementsBeforeFirstMonitor) {
                calculator.addElementToCalculate(element);
            }

            /* and end the segment with the first monitor */
            calculator.setEndSegmentMonitor(firstMonitor);
            calculator.setIsCycleStartSegment(true);
            this.calculators.add(calculator);
        }

        return true;
    }

    private OrbitSegmentCalculator createNewCalculator(JMadPlane plane, Element startSegmentMonitor) {
        OrbitSegmentCalculator calculator = new SimpleOrbitSegmentCalculator(plane);
        calculator.setStartSegmentMonitor(startSegmentMonitor);
        return calculator;
    }

    private List<Element> sortElementsByPosition(List<Element> elements) {
        LinkedList<Element> sorted = new LinkedList<Element>();

        for (Element element : elements) {
            if (element.getName().toUpperCase().startsWith("DRIFT")) {
                continue; // XXX maybe there is a better solution, for now just get rid of these!!
            }

            if (!sorted.isEmpty()) {
                Element first = sorted.getFirst();
                Element last = sorted.getLast();

                if (first.getPosition().getValue() > element.getPosition().getValue()) {
                    sorted.addFirst(element);
                    continue;
                }

                /* if same position we add after */
                if (last.getPosition().getValue() <= element.getPosition().getValue()) {
                    sorted.addLast(element);
                    continue;
                }
            } else {
                /* first element so just add */
                sorted.add(element);
                continue;
            }

            int i = 0;
            for (Element sortedElement : sorted) {
                if (element.getPosition().getValue() < sortedElement.getPosition().getValue()) {
                    /* found the first element after the current, replace */
                    break;
                }

                i++;
            }

            if (i > sorted.size()) {
                sorted.addLast(element);
            } else {
                sorted.add(i, element);
            }
        }

        return sorted;
    }

    /**
     * Clear the internal structure used for the orbit interpolation.
     * 
     * @param plane the {@link JMadPlane} for which to clear the interpolation structure.
     */
    private void clearStructure(JMadPlane plane) {
        Iterator<OrbitSegmentCalculator> iterator = this.calculators.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPlane() == plane) {
                iterator.remove();
            }
        }
    }

    /**
     * Update the calculators with the given optic.
     * 
     * @param optic the {@link Optic} to use for the update
     * @throws JMadException in case the structure is not yet created, or one of the calculators can not be updated.
     */
    private void updateToOptic(Optic optic) throws JMadException {
        if (this.calculators.size() == 0) {
            throw new JMadException("Could not update to optic, machine structure not defined.");
        }

        for (OrbitSegmentCalculator calculator : this.calculators) {
            if (!calculator.update(optic)) {
                throw new JMadException("Could not update to optic, update failed for " + calculator.getName());
            }
        }
    }

    @Override
    public synchronized OrbitInterpolationResult interpolate(OrbitInterpolationRequest request) throws JMadException {
        Orbit orbit = request.getOrbit();
        if (orbit == null) {
            throw new JMadException("Could not interpolate, no orbit data provided.");
        }

        if (this.calculators.size() == 0) {
            throw new JMadException("Could not interpolate, interpolation tool no properly updated");
        }

        OrbitInterpolationResultImpl result = new OrbitInterpolationResultImpl();
        for (OrbitSegmentCalculator calculator : this.calculators) {
            Map<Element, Map<MadxTwissVariable, Double>> valueMapping = calculator.calculate(request.getOrbit());
            if (valueMapping.size() == 0) {
                throw new JMadException("Could not interpolate, calculation of " + calculator.getName() + " failed");
            }

            result.addValuesPerPlane(calculator.getPlane(), valueMapping);
        }

        result.create();
        return result;
    }

    @Override
    public synchronized void update(UpdateRequest request) throws JMadException {
        for (JMadPlane plane : JMadPlane.values()) {
            if (request.updateStructure(plane)) {
                this.createStructure(plane, request.getMachineElements(), request.getMonitors(plane), request
                        .isCircularMachine());
            }
        }

        if (request.updateMachineOptics()) {
            this.updateToOptic(request.getOptic());
        }
    }
}
