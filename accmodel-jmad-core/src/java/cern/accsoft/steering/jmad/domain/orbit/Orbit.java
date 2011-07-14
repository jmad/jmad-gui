// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
package cern.accsoft.steering.jmad.domain.orbit;

import java.util.List;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * This interface represents an orbit for one plane.
 * 
 * @author muellerg
 */
public interface Orbit {

    /**
     * @return the list of monitor names defined for this orbit. the monitor names are in upper case in the orbit
     *         object.
     */
    public abstract List<String> getMonitorNames();

    /**
     * Retrieve the measured values for the given plane.
     * 
     * @param plane the {@link JMadPlane} to get the values for
     * @return the list of values
     */
    public abstract List<Double> getValues(JMadPlane plane);

    /**
     * Retrieve the index in the data list for the given monitor. The monitor name will be cast to upper case.
     * 
     * @param monitorName the name of the monitor to get the data index for
     * @return the index in the value list
     */
    public abstract Integer getMonitorIndex(String monitorName);
}
