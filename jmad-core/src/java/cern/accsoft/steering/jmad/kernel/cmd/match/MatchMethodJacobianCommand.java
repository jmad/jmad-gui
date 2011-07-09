/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.methods.AbstractMatchMethod;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodJacobian;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchMethodJacobianCommand extends AbstractMatchMethodCommand {

    /** the name of the command */
    private static final String CMD_NAME = "jacobian";

    public MatchMethodJacobianCommand(AbstractMatchMethod matchMethod) {
        super(matchMethod);
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    protected List<Parameter> getSpecialParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();

        if (getMatchMethod() instanceof MatchMethodJacobian) {
            MatchMethodJacobian method = (MatchMethodJacobian) getMatchMethod();
            parameters.add(new GenericParameter<Integer>("repeat", method.getRepeat()));

        }
        return parameters;

    }

}
