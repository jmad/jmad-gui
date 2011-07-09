/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.ptc;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;

/**
 * this class represents the madx command, to end a ptc-block.
 * 
 * @author kfuchsbe
 */
public class PtcEndCommand extends AbstractCommand {

	/** the name of the command */
	private static final String CMD_NAME = "ptc_end";

	@Override
	public String getName() {
		return CMD_NAME;
	}

}
