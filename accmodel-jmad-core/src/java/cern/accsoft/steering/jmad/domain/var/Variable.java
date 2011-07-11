package cern.accsoft.steering.jmad.domain.var;

/**
 * This is the general interface for a variable. It simple has a name and a unit.
 * 
 * @author kfuchsbe
 */
public interface Variable {

    /**
     * @return the name of the variable (can be arbitrary)
     */
    public abstract String getName();

    /**
     * @return a string, that represents the unit of the variable
     */
    public abstract String getUnit();

    /**
     * the type of the values which this variable represents
     * 
     * @return The class of the values
     */
    public abstract Class<?> getValueClass();

}