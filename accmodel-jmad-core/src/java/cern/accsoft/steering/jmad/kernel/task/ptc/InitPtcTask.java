/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.task.ptc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcAlignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcCreateLayoutCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcCreateUniverseCommand;
import cern.accsoft.steering.jmad.kernel.task.AbstractTask;

/**
 * initializes the PTC universe, creates the layout and calls the ptc-align
 * 
 * @author kaifox
 */
public class InitPtcTask extends AbstractTask {

    private static final int LAYOUT_DEFAULT_MODEL = 2;
    private static final int LAYOUT_DEFAULT_NST = 1;
    private static final int LAYOUT_DEFAULT_METHOD = 2;

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        commands.add(new PtcCreateUniverseCommand());

        PtcCreateLayoutCommand layoutCmd = new PtcCreateLayoutCommand();
        layoutCmd.setTime(false);
        layoutCmd.setModel(LAYOUT_DEFAULT_MODEL);
        layoutCmd.setMethod(LAYOUT_DEFAULT_METHOD);
        layoutCmd.setNst(LAYOUT_DEFAULT_NST);
        layoutCmd.setExact(true);
        layoutCmd.setResplit(true);
        layoutCmd.setThin(0.0005);
        layoutCmd.setXbend(0.0005);
        commands.add(layoutCmd);

        commands.add(new PtcAlignCommand());
        return commands;
    }

}
