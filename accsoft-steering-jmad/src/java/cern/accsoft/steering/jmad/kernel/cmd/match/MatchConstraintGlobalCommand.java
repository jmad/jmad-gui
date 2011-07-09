/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraintGlobal;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchConstraintGlobalCommand extends AbstractCommand {
    private static final String CMD_NAME = "global";

    @Override
    public String getName() {
        return CMD_NAME;
    }

    private final MatchConstraintGlobal actConstraint;
    private final String actSequ;

    public MatchConstraintGlobalCommand(MatchConstraintGlobal actConstraint, String sequenceName) {
        super();
        this.actConstraint = actConstraint;
        this.actSequ = sequenceName;
    }

    @Override
    public List<Parameter> getParameters() {

        List<Parameter> params = new ArrayList<Parameter>();

        params.add(new GenericParameter<String>("sequence", this.actSequ));

        Map<String, Double> paramSettings = this.actConstraint.getParameterSettings();

        for (Entry<String, Double> entry : paramSettings.entrySet()) {
            params.add(new GenericParameter<Double>(entry.getKey(), entry.getValue()));
        }

        return params;
    }

}
