package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequest;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.DeleteCommand;
import cern.accsoft.steering.jmad.kernel.cmd.TwissCommand;

public class RunTwiss extends AbstractResultSelectableTask {

    private static final String SELECT_FLAG_TWISS = "twiss";

    private TwissInitialConditions twiss = null;

    public RunTwiss(TwissInitialConditions twiss) {
        this(twiss, null);
    }

    public RunTwiss(TwissInitialConditions twissInitialConditions, TfsResultRequest resultRequest) {
        super(resultRequest);
        this.twiss = twissInitialConditions;
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        commands.add(new DeleteCommand("twiss"));
        commands.add(new DeleteCommand("summ"));
        commands.addAll(createSelectCommands(SELECT_FLAG_TWISS));    
        commands.add(new TwissCommand(twiss));
        return commands;
    }
}
