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
