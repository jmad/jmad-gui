package cern.accsoft.steering.jmad.kernel;

import java.io.File;

import cern.accsoft.steering.jmad.domain.result.ResultType;

public interface JMadExecutable {

    /**
     * returns a string which will be sent to madx in order to accomplish some Task in MadX.
     * 
     * @return the composed string for this executable that can be sent to MadX
     */
    public abstract String compose();

    public abstract ResultType getResultType();

    public abstract void setOutputFile(File outputFile);

    public abstract File getOutputFile();

}