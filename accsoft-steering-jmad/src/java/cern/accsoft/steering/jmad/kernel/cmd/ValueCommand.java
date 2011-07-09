package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * This class implements the MadX-Command 'value'.
 * 
 * @author kfuchsbe
 */
public class ValueCommand extends AbstractCommand {
    private static final String CMD_NAME = "value";

    private final Collection<String> valueNames;

    public ValueCommand(Collection<String> valueNames) {
        this.valueNames = valueNames;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        for (String valueName : valueNames) {
            parameters.add(new GenericParameter<Boolean>(valueName, true));
        }
        return parameters;
    }

}
