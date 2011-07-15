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

import cern.accsoft.steering.jmad.domain.var.GlobalVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * Special global variables for aperture files.
 * 
 * @author muellerg
 */
public enum MadxApertureGlobalVariable implements GlobalVariable {
    EXN, EYN, //
    DQF, //
    BETAQFX, //
    PARAS_DX, PARAS_DY, //
    DP_BUCKET_SIZE, //
    TWISS_DELTAP, //
    CO_RADIUS, //
    BETA_BEATING, //
    NB_OF_ANGLES, //
    HALO_PRIM, HALO_R, HALO_H, HALO_V, //
    N1MIN, //
    AT_ELEMENT(MadxVarType.STRING), //

    /* if something wrong: */
    UNKNOWN("jmad_unknown", MadxVarType.UNKNOWN);

    private String madxName;
    private MadxVarType type = MadxVarType.DOUBLE;

    private MadxApertureGlobalVariable() {
        /*
         * we use the lowercae expression as madx name.
         */
        this.madxName = this.name().toLowerCase();
    }

    private MadxApertureGlobalVariable(String madxName, MadxVarType type) {
        this(type);
        this.madxName = madxName;
    }

    private MadxApertureGlobalVariable(MadxVarType type) {
        this();
        this.type = type;
    }

    @Override
    public String getMadxName() {
        return this.madxName;
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
    public String getUnit() {
        return null;
    }

    @Override
    public Class<?> getValueClass() {
        return getVarType().getValueClass();
    }

    public static final MadxApertureGlobalVariable fromMadxName(String madxName) {
        return VariableUtil.findFromMadxName(MadxApertureGlobalVariable.class, madxName,
                MadxApertureGlobalVariable.UNKNOWN);
    }
}
