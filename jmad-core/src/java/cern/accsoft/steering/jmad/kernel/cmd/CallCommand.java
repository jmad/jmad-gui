package cern.accsoft.steering.jmad.kernel.cmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class CallCommand extends AbstractCommand {
    private static final String CMD_NAME = "call";

    /* the only parameter: */
    private File file;

    public CallCommand(File file) {
        setFile(file);
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<String>("file", file.getAbsolutePath(), true));
        return parameters;
    }

    public final void setFile(File file) {
        this.file = file;
    }
}
