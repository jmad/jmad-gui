/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.input.MadxVaryParameter;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchVaryParameterCommand extends AbstractCommand {
    private static final String CMD_NAME = "vary";

    @Override
    public String getName() {
        return CMD_NAME;
    }

    private final MadxVaryParameter actVaryParameter;

    public MatchVaryParameterCommand(MadxVaryParameter varyParameter) {
        super();
        this.actVaryParameter = varyParameter;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> params = new ArrayList<Parameter>();

        params.add(new GenericParameter<String>("name", this.actVaryParameter.getName()));
        params.add(new GenericParameter<Double>("step", this.actVaryParameter.getStep()));
        params.add(new GenericParameter<Double>("lower", this.actVaryParameter.getLower()));
        params.add(new GenericParameter<Double>("upper", this.actVaryParameter.getUpper()));
        return params;
    }
}
