package cern.accsoft.steering.jmad.kernel.cmd.seqedit;

import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SeqeditCommand extends AbstractCommand {

	/** the name of the sequence to edit */
	private String sequenceName = null;

	public SeqeditCommand(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	@Override
	public String getName() {
		return "seqedit";
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = super.getParameters();
		parameters.add(new GenericParameter<String>("sequence",
				this.sequenceName));
		return parameters;
	}

}
