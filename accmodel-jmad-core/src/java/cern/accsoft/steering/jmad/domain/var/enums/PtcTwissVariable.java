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
package cern.accsoft.steering.jmad.domain.var.enums;

import java.util.Set;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

public enum PtcTwissVariable implements TwissVariable {

    // ptc-variables
    PTC_DISP_X("disp1"), PTC_DISP2("disp2"), PTC_DISP_Y("disp3"), PTC_DISP4("disp4"), PTC_BETA11("beta11"), PTC_BETA12(
            "beta12"), PTC_BETA13("beta13"), PTC_BETA21("beta21"), PTC_BETA22("beta22"), PTC_BETA23("beta23"), PTC_BETA31(
            "beta31"), PTC_BETA32("beta32"), PTC_BETA33("beta33"),

    // if something wrong:
    UNKNOWN("jmad_unknown", MadxVarType.UNKNOWN);

    private String name;
    private String unit = null;
    private MadxVarType type;

    private PtcTwissVariable(String tag, MadxVarType type) {
        this.name = tag;
        this.type = type;
    }

    private PtcTwissVariable(String tag) {
        this(tag, MadxVarType.DOUBLE);
    }

    private PtcTwissVariable(String tag, String unit) {
        this(tag, MadxVarType.DOUBLE);
        this.unit = unit;
    }

    @Override
    public String getMadxName() {
        return name;
    }

    @Override
    public MadxVarType getVarType() {
        return type;
    }

    /**
     * Determine the correct Value of ResultVariable for a given tag.
     * 
     * @param madxName the tag for which to get the VarType - Value.
     * @return The VarType corresponding to the given tag.
     */
    public static final PtcTwissVariable fromMadxName(String madxName) {
        return VariableUtil.findFromMadxName(PtcTwissVariable.class, madxName, PtcTwissVariable.UNKNOWN);
    }

    /**
     * returns a set of variables of a given type.
     * 
     * @param type which variables to retrieve
     * @return the variables.
     */
    public static final Set<PtcTwissVariable> allOfType(MadxVarType type) {
        return VariableUtil.findFromVarType(PtcTwissVariable.class, type);
    }

    @Override
    public boolean isApertureVariable() {
        return false;
    }

    @Override
    public String getUnit() {
        return this.unit;
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
}
