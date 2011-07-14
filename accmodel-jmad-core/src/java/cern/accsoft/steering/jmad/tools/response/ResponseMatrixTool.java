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
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * This is the interface of a simple tool, which can calculate a response matrix.
 * 
 * @author kfuchsbe
 */
public interface ResponseMatrixTool {

    /**
     * calculates the actual response-matrix. Which correctors, monitors to use and how to vary the strengthes is
     * defined via the ResponseRequest object.
     * 
     * @param model the model to use to calculate the response matrix
     * @param request contains strenghtes, monitors and strength-variations to use to calc the response-matrix.
     * @return the response-matrix
     * @throws JMadModelException if the calculation of the response matrix fails
     */
    public abstract Matrix calcResponseMatrix(JMadModel model, ResponseRequest request) throws JMadModelException;

}
