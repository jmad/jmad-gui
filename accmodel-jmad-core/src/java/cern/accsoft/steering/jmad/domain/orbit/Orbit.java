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
