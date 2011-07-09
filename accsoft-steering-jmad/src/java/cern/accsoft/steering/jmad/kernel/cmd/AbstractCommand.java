package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public abstract class AbstractCommand extends AbstractJMadExecutable implements
		Command {

	@Override
	public final String compose() {
		StringBuffer command = new StringBuffer(getName());
		List<Parameter> parameters = getParameters();

		for (int i = 0; i < parameters.size(); i++) {
			Parameter param = parameters.get(i);
			if (param.isSet()) {
				command.append(", ");
				command.append(param.compose());
			}
		}
		command.append(';');

		return command.toString();
	}

	@Override
	public String toString() {
		return compose();
	}

	/**
	 * this may be overridden if the command has parameters.
	 */
	@Override
	public List<Parameter> getParameters() {
		return new ArrayList<Parameter>();
	}

}
