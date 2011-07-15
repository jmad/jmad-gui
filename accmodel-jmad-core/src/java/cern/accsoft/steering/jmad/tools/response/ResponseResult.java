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

/**
 * 
 */
package cern.accsoft.steering.jmad.tools.response;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * The result of a response-matrix calculation
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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
