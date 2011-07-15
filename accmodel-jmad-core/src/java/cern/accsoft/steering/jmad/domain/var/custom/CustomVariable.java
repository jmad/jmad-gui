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
package cern.accsoft.steering.jmad.domain.var.custom;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * This is the interface of a variable. It represents a madx definition of a key-value pair. A Variable may be e.g.
 * parsed from a strength file and then plotted in a gui. For the moment the expression of a variable can not be
 * explicitely set and the name can not be changed.
 * 
 * @author kfuchsbe
 */
public interface CustomVariable extends TwissVariable {

    /**
     * retrieve the expression of the variable. (The string which is used to calculate the variable within madx)
     * 
     * @return the expression as string
     */
    public String getExpression();

    /**
     * The comment for this variable. It is retrieved from the comment string in the same line as the variable
     * definition. (If there is one)
     * 
     * @return a comment concerning this variable
     */
    public String getComment();

    /**
     * @return true if the variable is defined by ":=", false if it is defined with "=".
     */
    public boolean isLateAssigned();

    /**
     * @return a unified key
     */
    public String getKey();
}
