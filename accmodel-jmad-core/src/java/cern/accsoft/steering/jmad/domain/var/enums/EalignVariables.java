/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.domain.var.enums;

import cern.accsoft.steering.jmad.domain.var.MadxVariable;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * Parameter values and variables names for Eoption and Esave commands.
 * 
 * @author agorzaws
 */
public enum EalignVariables implements MadxVariable {

    DX("dx", "m"),
    DY("dy", "m"),
    DS("ds", "m"),
    DPHI("dphi", "rad"),
    DTHETA("dtheta", "rad"),
    DPSI("dpsi", "rad"),
    MREX("mrex", ""),
    MREY("mrey", ""),
    MSCALX("mscalx", ""),
    MSCALY("mscaly", ""),
    AREX("arex", ""),
    AREY("arey", "");

    private String name;

    private String unit;

    private EalignVariables(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public Class<?> getValueClass() {
        return getVarType().getValueClass();
    }

    @Override
    public String getMadxName() {
        return getName();
    }

    @Override
    public MadxVarType getVarType() {
        return MadxVarType.DOUBLE;
    }

}
