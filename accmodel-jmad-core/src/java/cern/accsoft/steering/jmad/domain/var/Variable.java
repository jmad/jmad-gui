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
package cern.accsoft.steering.jmad.domain.var;

/**
 * This is the general interface for a variable. It simple has a name and a unit.
 * 
 * @author kfuchsbe
 */
public interface Variable {

    /**
     * @return the name of the variable (can be arbitrary)
     */
    public abstract String getName();

    /**
     * @return a string, that represents the unit of the variable
     */
    public abstract String getUnit();

    /**
     * the type of the values which this variable represents
     * 
     * @return The class of the values
     */
    public abstract Class<?> getValueClass();

}
