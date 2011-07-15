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

import java.util.Map;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.orbit.Orbit;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * This interface encapsulates the calls required to configure an region where to interpolate the orbit.
 * 
 * @author muellerg
 */
public interface OrbitSegmentCalculator {

    /**
     * @return the {@link JMadPlane} this orbit segment calculator works on
     */
    public abstract JMadPlane getPlane();

    /**
     * Set the monitor at the beginning of the segment
     * 
     * @param element the {@link Element} describing the monitor
     */
    public abstract void setStartSegmentMonitor(Element element);

    /**
     * Set the monitor at the ending of the segment
     * 
     * @param element the {@link Element} describing the monitor
     */
    public abstract void setEndSegmentMonitor(Element element);

    /**
     * Add an element to this calculator. For each update with a new monitor reading the orbit will be calculated.
     * 
     * @param element the element to add to the calculations in this segment
     */
    public abstract void addElementToCalculate(Element element);

    /**
     * Update the internal transfer matrices according to the optic provided.
     * 
     * @param optic the optic to update from
     * @return <code>true</code> if update was successful
     */
    public boolean update(Optic optic);

    /**
     * Interpolate the orbit for all defined elements. In case something goes wrong an empty map is returned.
     * 
     * @param orbit the orbit to interpolate from
     * @return the mapping between [element, orbit position] for all elements defined in the segment for the plane
     *         defined by {@link #getPlane()}
     */
    public abstract Map<Element, Double> calculate(Orbit orbit);

    /**
     * @return the name of the calculator.
     */
    public abstract String getName();
}
