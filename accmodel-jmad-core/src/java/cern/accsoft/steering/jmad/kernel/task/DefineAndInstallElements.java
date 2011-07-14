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

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.DefineElement;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.EndeditCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.FlattenCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.InstallCommand;
import cern.accsoft.steering.jmad.kernel.cmd.seqedit.SeqeditCommand;

/**
 * 
 * Define a list of element and add them to the sequence
 * This class has not been tested for usage online.
 * 
 * @author xbuffat
 *
 */

public class DefineAndInstallElements extends AbstractTask{

	private String sequence;
	private List<Element> toInstall;
	
	public DefineAndInstallElements(String sequ,List<Element> toInstall) {
		this.sequence = sequ;
		this.toInstall = toInstall;
	}
	
	@Override
	protected List<Command> getCommands() {
		List<Command> commands = new ArrayList<Command>();
		for(int i = 0;i<this.toInstall.size();++i)
		{
			commands.add(new DefineElement(this.toInstall.get(i)));
		}
		commands.add(new SeqeditCommand(this.sequence));
		for(int i = 0;i<this.toInstall.size();++i)
		{
			commands.add(new InstallCommand(this.toInstall.get(i)));
		}
		commands.add(new FlattenCommand());
		commands.add(new EndeditCommand());
		return commands;
	}

}
