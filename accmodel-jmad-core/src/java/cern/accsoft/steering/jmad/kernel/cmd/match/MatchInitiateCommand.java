/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.MatchResultRequest;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchInitiateCommand extends AbstractCommand {
    private static final String CMD_NAME = "match";

    private final MatchResultRequest request;

    public MatchInitiateCommand(MatchResultRequest actRequ) {
        this.request = actRequ;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<String>("sequence", this.request.getSequenceName()));
        if (this.request.getSaveBetaName() == null) {
            if (this.request.getInitialOpticsValues() != null) {
                TwissInitialConditions twiss = this.request.getInitialOpticsValues();
                for (MadxTwissVariable var : twiss.getMadxVariables()) {
                    parameters.add(new GenericParameter<Double>(var.getMadxName(), twiss.getValue(var)));

                }

            }
        } else {
            parameters.add(new GenericParameter<String>("beta0", this.request.getSaveBetaName(), true));
        }

        return parameters;
    }
}
