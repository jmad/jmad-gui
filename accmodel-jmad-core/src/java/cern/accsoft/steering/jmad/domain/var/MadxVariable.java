package cern.accsoft.steering.jmad.domain.var;

import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * This is the general interface for a variable. It simple has a name and a unit.
 * 
 * @author kfuchsbe
 */
public interface MadxVariable extends Variable {

    /**
     * retrieve the name of the variable. This must be a unique expression within the madx model.
     * 
     * @return the name of the variable.
     */
    public abstract String getMadxName();

    /**
     * @return the type (String/Double) of the variable
     */
    public abstract MadxVarType getVarType();

}