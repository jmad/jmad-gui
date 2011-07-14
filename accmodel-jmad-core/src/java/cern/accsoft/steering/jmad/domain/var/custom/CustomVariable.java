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
