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
package cern.accsoft.steering.jmad.domain.var;

import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * This is the general interface for a variable. It simple has a name and a unit.
 * 
 * @author kfuchsbe
 */
public interface MadxVariable extends Variable {

    /**
     * retrieve the name of the variable. This must be a unique expression within the madx model.
     * 
     * @return the name of the variable.
     */
    public abstract String getMadxName();

    /**
     * @return the type (String/Double) of the variable
     */
    public abstract MadxVarType getVarType();

}
