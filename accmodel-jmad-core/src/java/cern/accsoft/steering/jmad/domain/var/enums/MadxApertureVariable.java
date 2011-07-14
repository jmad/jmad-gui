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
package cern.accsoft.steering.jmad.domain.var.enums;

import cern.accsoft.steering.jmad.domain.var.ApertureVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * @author kfuchsbe
 */
public enum MadxApertureVariable implements ApertureVariable {
    // apertures
    APER_1("m"), APER_2("m"), APER_3("m"), APER_4("m"), APERTYPE(MadxVarType.STRING), //
    ON_AP, ON_ELEM, N1, N1X_M, N1Y_M, SPEC, RTOL, XTOL, YTOL;

    private String unit = null;

    private MadxVarType varType = MadxVarType.DOUBLE;

    private MadxApertureVariable() {
        /* nothing to do */
    }

    private MadxApertureVariable(MadxVarType varType) {
        this();
        this.varType = varType;
    }

    private MadxApertureVariable(String unit) {
        this();
        this.unit = unit;
    }

    @Override
    public String getMadxName() {
        return this.name().toLowerCase();
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public MadxVarType getVarType() {
        return this.varType;
    }

    @Override
    public String getName() {
        return getMadxName();
    }

    @Override
    public String toString() {
        return VariableUtil.toString(this);
    }

    @Override
    public Class<?> getValueClass() {
        return getVarType().getValueClass();
    }

    @Override
    public boolean isApertureVariable() {
        /*
         * all these variables have to be interpreted as aperture in the plots.
         */
        return true;
    }

}
