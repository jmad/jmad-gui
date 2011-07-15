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

package cern.accsoft.steering.jmad.tools.response;

import java.util.List;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * this interface defines, what kickers shall be used for calculating the response matrix and at what monitors the
 * response-values shall be calculated.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface ResponseRequest {

    /**
     * This method must return the names of the correctors which shall be used for calculating the response request.
     * 
     * @return The names of the correctors to use.
     */
    public abstract List<String> getCorrectorNames();

    /**
     * must return the planes in which the correctors shall be kicked.
     * 
     * @return the planes for the correctors.
     */
    public abstract List<JMadPlane> getCorrectorPlanes();

    /**
     * This method must return the strengthes which shall be used at the corrector for calculating the response at the
     * according correctors. The given strength will be applied once positive and once negative. The Response-matrix
     * element is then the difference of the two trajectories devided by twice the strength.
     * 
     * @return the strengths to use
     */
    public abstract List<Double> getStrengthValues();

    /**
     * The monitor names.
     * 
     * @return the names of the monitors at which positions the the response shall be calculated.
     */
    public abstract List<String> getMonitorNames();

    /**
     * must return a plane for each of monitors, at which the position shall be taken to calc the response matrix
     * element.
     * 
     * @return the monitor-planes
     */
    public abstract List<JMadPlane> getMonitorPlanes();

    /**
     * must return regular expressions representing the monitors. This makes the twiss faster then using all the names
     * seperately as filter.
     * 
     * @return a list of regular expressions.
     */
    public abstract List<String> getMonitorRegexps();

}
