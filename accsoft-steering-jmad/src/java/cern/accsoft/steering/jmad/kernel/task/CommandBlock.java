package cern.accsoft.steering.jmad.kernel.task;

import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.Command;

/**
 * this block just represents a block of commands which is executed in one go.
 * 
 * @author kfuchsbe
 */
public class CommandBlock extends AbstractTask {
    private final List<Command> commands;

    public CommandBlock(List<Command> commands) {
        super();
        this.commands = commands;
    }

    @Override
    protected List<Command> getCommands() {
        return this.commands;
    }

}
