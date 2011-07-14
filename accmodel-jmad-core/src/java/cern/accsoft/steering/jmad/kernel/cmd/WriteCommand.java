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

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * Command write,table=tableName,file=fileName;
 * 
 * @author xbuffat
 *
 */

public class WriteCommand extends AbstractCommand {

	private static final String CMD_NAME = "write";
	
	private String tableName;
	private String file;
	
	public WriteCommand(String tableName) {
		this(tableName,null);
	}
	
	public WriteCommand(String tableName, String file) {
		this.tableName = tableName;
		this.file = file;
	}
	
	@Override
	public String getName() {
		return WriteCommand.CMD_NAME;
	}
	
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		parameters.add(new GenericParameter<String>("table",this.tableName,true));
		if(file!=null)
		{
			parameters.add(new GenericParameter<String>("file",this.file,true));
		}
		return parameters;
	}

}
