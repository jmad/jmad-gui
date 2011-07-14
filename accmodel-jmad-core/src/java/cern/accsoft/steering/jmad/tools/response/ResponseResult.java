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
/**
 * 
 */
package cern.accsoft.steering.jmad.tools.response;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * The result of a response-matrix calculation
 * 
 * @author kfuchsbe
 */
public interface ResponseResult {

    /**
     * @return the full calculated Response matrix (H+V)
     */
    public Matrix getResponseMatrix();

    /**
     * the trajectory of a response calculation for one plane and one sign
     * 
     * @param plane the plane for which to get the trajectory
     * @param sign the sign for which to get the trajectory
     * @return a matrix which contains the trajectories for the given plane and sign
     */
    public Matrix getTrajectoryMatrix(JMadPlane plane, DeflectionSign sign);
}
