/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodMigrad;

/**
 * @author muellerg
 */
public class MatchMethodMigradCommand extends AbstractMatchMethodCommand {

    public MatchMethodMigradCommand(MatchMethodMigrad matchMethod) {
        super(matchMethod);
        /*
         * nothing special here. this command has only the default options of MatchMethodCommand.
         */
    }

    /** the name of the command */
    private static final String CMD_NAME = "migrad";

    @Override
    public String getName() {
        return CMD_NAME;
    }

}
