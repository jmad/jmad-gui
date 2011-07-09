/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.match;

import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodLmdif;

/**
 * @author muellerg
 */
public class MatchMethodLmdifCommand extends AbstractMatchMethodCommand {

    public MatchMethodLmdifCommand(MatchMethodLmdif matchMethod) {
        super(matchMethod);
        /*
         * nothing special here. this command has only the default options of MatchMethodCommand.
         */
    }

    /** the name of the command */
    private static final String CMD_NAME = "lmdif";

    @Override
    public String getName() {
        return CMD_NAME;
    }

}
