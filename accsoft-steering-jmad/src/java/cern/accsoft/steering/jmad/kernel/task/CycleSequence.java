package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.CycleCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.EndeditCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.FlattenCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.SeqeditCommand;

public class CycleSequence extends AbstractTask {

	private RangeDefinition rangeDefinition;

	public CycleSequence(RangeDefinition rangeDefinition) {
		this.rangeDefinition = rangeDefinition;
	}

	@Override
	protected List<Command> getCommands() {
		List<Command> commands = new ArrayList<Command>();
		if (this.rangeDefinition.getStartElementName() != null) {
			commands.add(new SeqeditCommand(rangeDefinition
					.getSequenceDefinition().getName()));
			commands.add(new FlattenCommand());
			commands
					.add(new CycleCommand(rangeDefinition.getStartElementName()));
			commands.add(new EndeditCommand());
		}
		return commands;
	}
}
