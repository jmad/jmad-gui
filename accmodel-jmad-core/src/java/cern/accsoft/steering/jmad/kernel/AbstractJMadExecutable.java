package cern.accsoft.steering.jmad.kernel;

import java.io.File;

import cern.accsoft.steering.jmad.domain.result.ResultType;

/**
 * Contains common functions of commands/tasks that can be executed in MadX.
 * 
 * @author kfuchsbe
 */
public abstract class AbstractJMadExecutable implements JMadExecutable {

    /** The output-file, which can be used in the compose method. */
    private File outputFile = null;

    /**
     * The {@link ResultType} which is produced by this command. This value is then used by the kernel, in order to
     * determine which parser to use to determine the type of parser to use for parsing the madx-output.
     * <p>
     * Shall be overridden by subclass, if it provides a result.
     * 
     * @return the result type for this executable
     */
    @Override
    public ResultType getResultType() {
        return ResultType.NO_RESULT;
    }

    /**
     * set the output-file. This should be only done by the {@link JMadKernel} since the kernel is the only one who
     * knows where the output is needed to parse it afterwards.
     * 
     * @param outputFile the new output file to set
     */
    public final void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * the actual output-file.
     * 
     * @return the file to which the output shall be written
     */
    public final File getOutputFile() {
        return outputFile;
    }

}
