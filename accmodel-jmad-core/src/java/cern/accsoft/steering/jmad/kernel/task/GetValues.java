package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.kernel.cmd.AssignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.SetFormatCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ValueCommand;

public class GetValues extends AbstractTask {

    private final Collection<String> valueNames;

    public GetValues(Collection<String> valueNames) {
        this.valueNames = valueNames;
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        commands.add(new SetFormatCommand(SetFormatCommand.DEFAULT_FORMAT_FLOAT));
        commands.add(new AssignCommand(getOutputFile()));
        commands.add(new ValueCommand(valueNames));
        commands.add(new AssignCommand());
        return commands;
    }

    @Override
    public ResultType getResultType() {
        if ((valueNames == null) || (valueNames.isEmpty()) || (getOutputFile() == null)) {
            return super.getResultType();
        } else {
            return ResultType.VALUES_RESULT;
        }
    }
}
