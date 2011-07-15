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

import java.util.Set;

import cern.accsoft.steering.jmad.domain.var.GlobalVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * This enum represents madx global variables, which appear in general in the sum-table of the twiss. TODO define units
 * 
 * @author kfuchsbe
 */
public enum PtcGlobalVariable implements GlobalVariable {

    NAME(MadxVarType.STRING), //
    TYPE(MadxVarType.STRING), //
    SEQUENCE(MadxVarType.STRING), //

    /*
     * beam parameters
     */
    PARTICLE(MadxVarType.STRING), //
    MASS, CHARGE, ENERGY, PC, GAMMA, KBUNCH, BCURRENT, //
    SIGE, SIGT, NPART, EX, EY, ET, //

    /*
     * elements of sum-table
     */
    LENGTH, DELTAP, //
    ALPHA_C, ALPHA_C_P, ALPHA_C_P2, ALPHA_C_P3, ETA_C, GAMMA_TR, //
    Q1, Q2, DQ1, DQ2, QS, //
    BETA_X_MIN, BETA_X_MAX, BETA_Y_MIN, BETA_Y_MAX, //
    ORBIT_X, ORBIT_PX, ORBIT_Y, ORBIT_PY, ORBIT_PT, //
    ORBIT_CT("ORBIT_-CT", MadxVarType.DOUBLE), //
    XCORMS, PXCORMS, YCORMS, PYCORMS, //
    XCOMAX, PXCOMAX, YCOMAX, PYCOMAX, //

    /*
     * others
     */
    TITLE(MadxVarType.STRING), //
    ORIGIN(MadxVarType.STRING), //
    DATE(MadxVarType.STRING), //
    TIME(MadxVarType.STRING),

    /* if something wrong: */
    UNKNOWN("jmad_unknown", MadxVarType.UNKNOWN);

    private String madxName;
    private String unit = null;
    private MadxVarType type = MadxVarType.DOUBLE;

    /*
     * constructors
     */

    private PtcGlobalVariable() {
        /*
         * we use the lowercae expression as madx name.
         */
        this.madxName = this.name().toLowerCase();
    }

    private PtcGlobalVariable(String unit) {
        this();
        this.unit = unit;
    }

    private PtcGlobalVariable(String madxName, MadxVarType varType) {
        this(varType);
        this.madxName = madxName;
    }

    private PtcGlobalVariable(MadxVarType type) {
        this();
        this.type = type;
    }

    public static final PtcGlobalVariable fromMadxName(String madxName) {
        return VariableUtil.findFromMadxName(PtcGlobalVariable.class, madxName, PtcGlobalVariable.UNKNOWN);
    }

    public static final Set<PtcGlobalVariable> allOfType(MadxVarType varType) {
        return VariableUtil.findFromVarType(PtcGlobalVariable.class, varType);
    }

    //
    // methods of interface Variable
    //

    @Override
    public String getMadxName() {
        return this.madxName;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public MadxVarType getVarType() {
        return this.type;
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
