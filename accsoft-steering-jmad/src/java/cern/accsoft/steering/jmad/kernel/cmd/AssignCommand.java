package cern.accsoft.steering.jmad.kernel.cmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class AssignCommand extends AbstractCommand {
    private static final String CMD_NAME = "assign";

    /** The file to which the madx output should be directed to */
    private final File file;

    /**
     * Constructor for resetting the echo to terminal
     */
    public AssignCommand() {
        this.file = null;
    }

    /**
     * Constructor to redirect echo for commands value, show and print to a specific file.
     * 
     * @param file the file to which to redirect the output.
     */
    public AssignCommand(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        if (file == null) {
            parameters.add(new GenericParameter<String>("echo", "terminal", false));
        } else {
            parameters.add(new GenericParameter<String>("echo", file.getAbsolutePath(), true));
        }
        return parameters;
    }

}
