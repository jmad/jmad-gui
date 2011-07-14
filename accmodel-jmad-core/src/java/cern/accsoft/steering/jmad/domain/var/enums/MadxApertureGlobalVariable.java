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
