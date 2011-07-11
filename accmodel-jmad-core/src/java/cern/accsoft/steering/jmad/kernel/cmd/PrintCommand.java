package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * This class implements the MadX-Command 'print' to current output file.
 * 
 * @author muellerg
 */
public class PrintCommand extends AbstractCommand {
    private static final String CMD_NAME = "print";

    private final String printText;

    public PrintCommand(String text) {
        this.printText = text;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<String>("text", this.printText, true));

        return parameters;
    }

}
