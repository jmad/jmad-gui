// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
