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
