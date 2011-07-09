package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.List;

import cern.accsoft.steering.jmad.kernel.JMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public interface Command extends JMadExecutable {

    /**
     * has to be implemented in order to return the name of the command (keyword);
     * 
     * @return the name of the command (MadX keyword)
     */
    public abstract String getName();

    /**
     * has to be implemented in subclass in order to return the ArrayList of parameters of the command.
     * 
     * @return all the parameter for the command
     */
    public abstract List<Parameter> getParameters();

    /**
     * composes the final command and returns it as String.
     * 
     * @return the total Command-String.
     */
    public abstract String compose();

}