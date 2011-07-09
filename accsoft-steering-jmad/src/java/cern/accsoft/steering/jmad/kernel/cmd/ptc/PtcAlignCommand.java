/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.ptc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * this class represents the madx command, to end a ptc-block.
 * 
 * @author kfuchsbe
 */
public class PtcAlignCommand extends AbstractCommand {

    /** the name of the command */
    private static final String CMD_NAME = "ptc_align";

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        /* this command has no parameters! */
        return new ArrayList<Parameter>();
    }

}
