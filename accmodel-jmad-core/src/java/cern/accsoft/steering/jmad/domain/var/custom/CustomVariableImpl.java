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

import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * This class is the basic implementation of a {@link CustomVariable}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class CustomVariableImpl implements CustomVariable {

    /** The name of the variable */
    private final String name;

    /** The expression-string of the variable */
    private final String expression;

    /** The comment for the variable */
    private final String comment;

    /** True if defined with ":=", false if defined with "=" */
    private final boolean lateAssigned;

    /**
     * The constructor which enforces to give a name and the expression.
     * 
     * @param name the name of the variable
     * @param expression the expression defining the variable in madx.
     * @param comment a simple description of the variable
     * @param lateAssigned true, if assigned by ":=", false if assigned by ":".
     */
    public CustomVariableImpl(String name, String expression, String comment, boolean lateAssigned) {
        this.name = name;
        this.expression = expression;
        this.comment = comment;
        this.lateAssigned = lateAssigned;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public String getMadxName() {
        return this.name;
    }

    @Override
    public boolean isLateAssigned() {
        return this.lateAssigned;
    }

    @Override
    public boolean isApertureVariable() {
        /*
         * this is just a primitive implementation. Maybe this shall become somehow selectable!?
         */
        return (getMadxName().toUpperCase().contains("APER") || ((getComment() != null) && getComment().toUpperCase()
                .contains("APERTURE")));
    }

    @Override
    public String toString() {
        return getMadxName();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CustomVariable) {
            return this.getMadxName().equals(((CustomVariable) other).getMadxName());
        } else {
            return super.equals(other);
        }
    }

    @Override
    public int hashCode() {
        return this.getMadxName().hashCode();
    }

    @Override
    public String getKey() {
        return getMadxName().trim().toLowerCase();
    }

    @Override
    public String getUnit() {
        return null;
    }

    @Override
    public MadxVarType getVarType() {
        return MadxVarType.DOUBLE;
    }

    @Override
    public String getName() {
        return getMadxName();
    }

    @Override
    public Class<?> getValueClass() {
        return getVarType().getValueClass();
    }

}
