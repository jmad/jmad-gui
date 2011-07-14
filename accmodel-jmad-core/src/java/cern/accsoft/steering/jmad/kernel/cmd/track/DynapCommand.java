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
package cern.accsoft.steering.jmad.kernel.cmd.track;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.result.track.DynapResultRequest;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * Command DYNAP,TURNS=real, FASTUNE=logical,LYAPUNOV=real,MAXAPER:={..,..,..,..,..,..},ORBIT=logical;
 * 
 * @author xbuffat
 *
 */

public class DynapCommand extends AbstractCommand {

	private static final String CMD_NAME = "dynap";
	
	private static final Logger LOGGER = Logger.getLogger(DynapCommand.class);
	
	private final DynapResultRequest dynapResultRequest;
	
	public DynapCommand(DynapResultRequest request) {
		this.dynapResultRequest = request;
	}
	
	@Override
	public String getName() {
		return DynapCommand.CMD_NAME;
	}
	
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		parameters.add(new GenericParameter<Integer>("turns",this.getDynapResultRequest().getTurns()));
		parameters.add(new GenericParameter<Double>("lyapunov",this.getDynapResultRequest().getLyapunov()));
		parameters.add(new GenericParameter<Boolean>("fastune",this.getDynapResultRequest().isFastTune()));
		parameters.add(new GenericParameter<Boolean>("orbit",this.getDynapResultRequest().isOrbit()));
		if(this.getDynapResultRequest().isApertureLimited())
		{
			LOGGER.warn("Aperture limitation is not avilable yet, no aperture limitation set");
		}
		
		return parameters;
	}

	public DynapResultRequest getDynapResultRequest() {
		return dynapResultRequest;
	}

}
