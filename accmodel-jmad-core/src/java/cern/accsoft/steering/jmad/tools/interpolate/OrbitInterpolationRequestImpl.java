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

import cern.accsoft.steering.jmad.domain.orbit.Orbit;

/**
 * A request for orbit interpolation. Simple implementation only taking the desired orbit object as constructor
 * argument.
 * 
 * @author muellerg
 */
public class OrbitInterpolationRequestImpl implements OrbitInterpolationRequest {

    /** the orbit to interpolate to */
    private Orbit orbit;

    public OrbitInterpolationRequestImpl(Orbit orbit) {
        this.orbit = orbit;
    }

    @Override
    public Orbit getOrbit() {
        return this.orbit;
    }

}
