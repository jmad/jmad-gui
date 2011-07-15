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
