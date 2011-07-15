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
 * The implementation of a ResponseResult
 * 
 * @author kfuchsbe
 */
public class ResponseResultImpl implements ResponseResult {

    private final Matrix responseMatrix;

    public ResponseResultImpl(int monitorCount, int correctorCount) {
        this.responseMatrix = new Matrix(correctorCount, monitorCount);
    }

    @Override
    public Matrix getResponseMatrix() {
        return this.responseMatrix;
    }

    @Override
    public Matrix getTrajectoryMatrix(JMadPlane plane, DeflectionSign sign) {
        // TODO Auto-generated method stub
        return null;
    }

}
