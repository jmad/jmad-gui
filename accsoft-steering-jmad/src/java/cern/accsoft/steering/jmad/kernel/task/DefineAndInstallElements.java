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
