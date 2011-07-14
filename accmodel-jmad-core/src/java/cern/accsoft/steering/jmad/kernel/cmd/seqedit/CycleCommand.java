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
