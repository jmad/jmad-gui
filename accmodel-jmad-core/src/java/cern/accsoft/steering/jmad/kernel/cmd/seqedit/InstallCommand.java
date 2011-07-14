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

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.Position;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class InstallCommand extends AbstractCommand {

	private static final String CMD_NAME = "install";
	
	private String elementName;
	private String elementClass;
	private Position elementPosition;
	
	public InstallCommand(Element element) {
		this.elementName = element.getName();
		this.elementClass = element.getMadxElementType().getMadxName();
		this.elementPosition = element.getPosition();
	}
	
	@Override
	public String getName() {
		return InstallCommand.CMD_NAME;
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new GenericParameter<String>("element",this.elementName));
		parameters.add(new GenericParameter<String>("class",this.elementClass));
		parameters.add(new GenericParameter<Double>("at",this.elementPosition.getValue()));
		if(this.elementPosition.isRelative())
		{
			parameters.add(new GenericParameter<String>("from",this.elementPosition.getElement()));
		}
		
		return parameters;
	}
	
}
