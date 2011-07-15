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

package cern.accsoft.steering.jmad.domain.var.enums;

import java.util.Set;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

public enum MadxTwissVariable implements TwissVariable {

    // generic stuff
    NAME("name", MadxVarType.STRING), //
    KEYWORD("keyword", MadxVarType.STRING), //
    PARENT("parent", MadxVarType.STRING),

    // canonical variables
    X("x", "m"), PX("px", "rad"), Y("y", "m"), PY("py", "rad"), T("t"), PT("pt"), DELTAP("deltap"),

    // independent variable
    S("s", "m"), L("l", "m"),

    // normalized variables and other derived quantities
    XN("xn"), PXN("pxn"), WX("wx"), PHIX("phix"), YN("yn"), PYN("pyn"), WY("wy"), PHIY("phiy"), TN("tn"), PTN("ptn"), WT(
            "wt"), PHIT("phit"),

    // linear lattice functions
    BETX("betx", "m"), ALFX("alfx"), MUX("mux", "2pi"), DX("dx", "m"), DPX("dpx"), BETY("bety", "m"), ALFY("alfy"), MUY(
            "muy", "2pi"), DY("dy", "m"), DPY("dpy"),

    // additional for twiss table:
    GAMX("gamx"), GAMY("gamy"), SIGX("sigx"), SIGY("sigy"),

    // chromatic functions
    DMUX("dmux"), DDX("ddx"), DDPX("ddpx"), DMUY("dmuy"), DDY("ddy"), DDPY("ddpy"), DBX("dbx"), DBY("dby"),

    // k-values
    K0L("k0l"), // dipole
    K1L("k1l"), // quad
    K2L("k2l"), // sextupole
    K3L("k3l"), // ocupole
    K4L("k4l"), // decapole
    K5L("k5l"), // dodecapole
    // skew k-values
    K0SL("k0sl"), // dipole
    K1SL("k1sl"), // quad
    K2SL("k2sl"), // sextupole
    K3SL("k3sl"), // ocupole
    K4SL("k4sl"), // decapole

    HKICK("hkick"), VKICK("vkick"), // kickers...
    POLARITY("polarity"), // and the assigned polarity

    // 2x2 coupling matrix coefficients
    R11("r11"), R12("r12"), R21("r21"), R22("r22"),

    // if something wrong:
    UNKNOWN("jmad_unknown", MadxVarType.UNKNOWN);

    private String name;
    private String unit = null;
    private MadxVarType type;

    private MadxTwissVariable(String tag, MadxVarType type) {
        this.name = tag;
        this.type = type;
    }

    private MadxTwissVariable(String tag) {
        this(tag, MadxVarType.DOUBLE);
    }

    private MadxTwissVariable(String tag, String unit) {
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
     * Determine the correct Value of Variable for a given tag.
     * 
     * @param madxName the tag for which to get the VarType - Value.
     * @return The VarType corresponding to the given tag.
     */
    public static final MadxTwissVariable fromMadxName(String madxName) {
        return VariableUtil.findFromMadxName(MadxTwissVariable.class, madxName, MadxTwissVariable.UNKNOWN);
    }

    /**
     * returns a set of variables of a given type.
     * 
     * @param type which variables to retrieve
     * @return the variables.
     */
    public static final Set<MadxTwissVariable> allOfType(MadxVarType type) {
        return VariableUtil.findFromVarType(MadxTwissVariable.class, type);
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
