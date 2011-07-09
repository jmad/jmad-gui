package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SetEqual extends AbstractJMadExecutable implements Command {
    private final String valueName;
    private final Double value;

    public SetEqual(String valueName, Double value) {
        this.valueName = valueName;
        this.value = value;
    }

    @Override
    public String compose() {
        return valueName + " = " + value + ";";
    }

    @Override
    public String getName() {
        return this.valueName;
    }

    @Override
    public List<Parameter> getParameters() {
        /*
         * XXX check if really necessary, that this implements a command
         */
        List<Parameter> retVal = new ArrayList<Parameter>();
        retVal.add(new GenericParameter<Double>(this.valueName, this.value));
        return retVal;
    }
}
