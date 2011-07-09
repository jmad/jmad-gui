package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SetListEqual extends AbstractJMadExecutable implements Command {
    private final Map<String, Double> valuePairs;

    public SetListEqual(Map<String, Double> valuePairs) {
        this.valuePairs = valuePairs;
    }

    @Override
    public String compose() {
        StringBuffer cmd = new StringBuffer("");
        for (String valueName : this.valuePairs.keySet()) {
            cmd.append(valueName + " = " + this.valuePairs.get(valueName) + ";\n");
        }
        return cmd.toString();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public List<Parameter> getParameters() {
        return new ArrayList<Parameter>(0);
    }
}
