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
