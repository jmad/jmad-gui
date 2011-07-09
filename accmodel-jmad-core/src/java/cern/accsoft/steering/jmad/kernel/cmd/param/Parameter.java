package cern.accsoft.steering.jmad.kernel.cmd.param;

/**
 * this interface represents all kind of possible parameters to madx-commands
 * 
 * @author kfuchsbe
 */
public interface Parameter {

    /**
     * has to be implemented in subclass to return the correct "name=value" - syntax.
     * 
     * @return a string representing the correct syntax for MadX
     */
    public abstract String compose();

    /**
     * has to be implemented in subclass to determine if the parameter is set or not.
     * 
     * @return true, if set, false otherwise.
     */
    public abstract boolean isSet();

}