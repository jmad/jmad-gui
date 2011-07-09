package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SaveBetaCommand extends AbstractCommand {
    private static final String CMD_NAME = "savebeta";

    private final String label;
    private final String location;
    private final String sequName;

    public SaveBetaCommand(String name, String location, String sequenceName) {
        this.label = name;
        this.location = location;
        this.sequName = sequenceName;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<String>("label", this.label, true));
        parameters.add(new GenericParameter<String>("place", this.location));
        parameters.add(new GenericParameter<String>("sequence", this.sequName));
        return parameters;
    }
}
