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
package cern.accsoft.steering.jmad.domain.var;

import java.util.EnumSet;
import java.util.Set;

import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * This class provides some methods to handle variables which are implemented in enums.
 * 
 * @author kfuchsbe
 */
public final class VariableUtil {

    private VariableUtil() {
        /* only static methods */
    }

    /**
     * finds all the variables which are contained in the given enum which have the given varType
     * 
     * @param <T> the enum type from which to get the variables
     * @param enumClass the class of the enum which to search for variables
     * @param varType the type of variables to return
     * @return a set of those Variables
     */
    public static <T extends Enum<T> & MadxVariable> Set<T> findFromVarType(Class<T> enumClass, MadxVarType varType) {
        EnumSet<T> vars = EnumSet.noneOf(enumClass);
        for (T var : enumClass.getEnumConstants()) {
            if (var.getVarType().equals(varType)) {
                vars.add(var);
            }
        }
        return vars;
    }

    /**
     * finds the variable out of the given enum which corresponds to the given madx-name.
     * 
     * @param <T> the enum type from which to find the variables
     * @param enumClass the class of the enum which to search for the variable
     * @param madxName the madx name of the variable to find
     * @param defaultValue the value which is returned, if no variable could be found from the name
     * @return the variable if found, or <code>null</code> if no variable could be found.
     */
    public static <T extends Enum<T> & MadxVariable> T findFromMadxName(Class<T> enumClass, String madxName,
            T defaultValue) {
        for (T var : enumClass.getEnumConstants()) {
            if (var.getMadxName().equalsIgnoreCase(madxName)) {
                return var;
            }
        }
        return defaultValue;
    }

    /**
     * composes the name and unit of the variable to a string
     * 
     * @param var the variable to convert to a string
     * @return the string representation of the variable
     */
    public static String toString(Variable var) {
        StringBuffer buffer = new StringBuffer(var.getName());
        if (var.getUnit() != null) {
            buffer.append(" [");
            buffer.append(var.getUnit());
            buffer.append(']');
        }
        return buffer.toString();
    }
}
