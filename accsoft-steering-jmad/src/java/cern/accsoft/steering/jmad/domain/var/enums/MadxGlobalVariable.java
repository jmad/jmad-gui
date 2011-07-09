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
public enum MadxGlobalVariable implements GlobalVariable {
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
    LENGTH, ALFA, ORBIT5, GAMMATR, // 
    Q1, Q2, DQ1, DQ2, // DDQ1, DDQ2, --> to be verified... //
    DXMAX, DYMAX, XCOMAX, YCOMAX, BETXMAX, BETYMAX, //
    XCORMS, YCORMS, DXRMS, DYRMS, DELTAP, //
    SYNCH_1, SYNCH_2, SYNCH_3, SYNCH_4, SYNCH_5,

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

    private MadxGlobalVariable() {
        /*
         * we use the lowercae expression as madx name.
         */
        this.madxName = this.name().toLowerCase();
    }

    private MadxGlobalVariable(String unit) {
        this();
        this.unit = unit;
    }

    private MadxGlobalVariable(String madxName, MadxVarType varType) {
        this(varType);
        this.madxName = madxName;
    }

    private MadxGlobalVariable(MadxVarType type) {
        this();
        this.type = type;
    }

    public static final MadxGlobalVariable fromMadxName(String madxName) {
        return VariableUtil.findFromMadxName(MadxGlobalVariable.class, madxName, MadxGlobalVariable.UNKNOWN);
    }

    public static final Set<MadxGlobalVariable> allOfType(MadxVarType varType) {
        return VariableUtil.findFromVarType(MadxGlobalVariable.class, varType);
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
