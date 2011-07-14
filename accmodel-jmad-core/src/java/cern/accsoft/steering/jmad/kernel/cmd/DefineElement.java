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
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class DefineElement extends AbstractJMadExecutable implements Command {

	private Element element;
	
	public DefineElement(Element element) {
		this.element = element;
	}
	
	@Override
	public String compose() {
		String retVal = this.getName()+": "+this.element.getMadxElementType().getMadxName();
		for(Parameter parameter : this.getParameters())
		{
			retVal+=", "+parameter.compose();
		}
		retVal+=";";
		return retVal;
	}

	@Override
	public String getName() {
		return this.element.getName();
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for(String attribute : this.element.getAttributeNames())
		{
			if(this.element.getAttribute(attribute) != null)
			{
				parameters.add(new GenericParameter<Double>(attribute,this.element.getAttribute(attribute)));
			}
		}
		return parameters;
	}

}
