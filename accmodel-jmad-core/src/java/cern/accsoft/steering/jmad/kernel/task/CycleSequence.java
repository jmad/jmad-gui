// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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
