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
package cern.accsoft.steering.jmad.domain.var.enums;

import cern.accsoft.steering.jmad.domain.var.ApertureVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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
