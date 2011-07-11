/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodSimplex;

/**
 * @author muellerg
 */
public class MatchMethodSimplexCommand extends AbstractMatchMethodCommand {

    public MatchMethodSimplexCommand(MatchMethodSimplex matchMethod) {
        super(matchMethod);
        /*
         * nothing special here. this command has only the default options of MatchMethodCommand.
         */
    }

    /** the name of the command */
    private static final String CMD_NAME = "simplex";

    @Override
    public String getName() {
        return CMD_NAME;
    }

}
