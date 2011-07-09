package cern.accsoft.steering.jmad.kernel.cmd.seqedit;

import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class CycleCommand extends AbstractCommand {

	/** The name of the element where the sequence should start */
	private String startElementName = null;

	public CycleCommand(String startElementName) {
		this.startElementName = startElementName;
	}

	@Override
	public String getName() {
		return "cycle";
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = super.getParameters();
		parameters.add(new GenericParameter<String>("start",
				this.startElementName));
		return parameters;
	}

}
