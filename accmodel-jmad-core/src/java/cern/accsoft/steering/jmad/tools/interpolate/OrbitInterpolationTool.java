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

import cern.accsoft.steering.jmad.JMadException;

/**
 * This interface provides the access to the interpolation of orbits.
 * 
 * @author muellerg
 */
public interface OrbitInterpolationTool {

    /**
     * Update the orbit interpolation tool with the given {@link UpdateRequest}. This method MUST be called at least
     * once with a request that initializes the structure (
     * {@link UpdateRequest#updateStructure(cern.accsoft.steering.jmad.domain.types.enums.JMadPlane)} returns true for
     * all planes required) before the interpolation is used.
     * 
     * @param request the {@link UpdateRequest} containing the data to be used for the update.
     * @throws JMadException in case the update fails
     */
    public abstract void update(UpdateRequest request) throws JMadException;

    /**
     * Perform an interpolation according to the given {@link OrbitInterpolationRequest}. {@link #update(UpdateRequest)}
     * MUST be called at least once before the first usage.
     * 
     * @param request the request containing the information about what to interpolate
     * @return the result of the interpolation
     * @throws JMadException in case something goes wrong during the interpolation
     */
    public abstract OrbitInterpolationResult interpolate(OrbitInterpolationRequest request) throws JMadException;
}
