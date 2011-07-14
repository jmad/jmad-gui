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

import cern.accsoft.steering.jmad.domain.result.track.TrackResultRequest;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * command RUN, maxaper= double array, turns= integer, ffile= integer;
 * 
 * @author xbuffat
 *
 */

public class TrackRunCommand extends AbstractCommand {

	private static final Logger LOGGER = Logger.getLogger(TrackRunCommand.class);
	
	private static final String CMD_NAME = "run";
	
	private final TrackResultRequest trackRequestResult;
	
	public TrackRunCommand(TrackResultRequest trackResultRequest) {
		this.trackRequestResult = trackResultRequest;
	}
	
	@Override
	public String getName() {
		return TrackRunCommand.CMD_NAME;
	}
	
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		if(this.trackRequestResult.isApertureLimited())
		{
			LOGGER.warn("maxaper not available yet, no aperture defined");
//			parameters.add(new GenericParameter<Double[]>("maxaper",this.trackRequestResult.getApertureLimitation()));
		}
		parameters.add(new GenericParameter<Integer>("turns",this.trackRequestResult.getTurns()));
		parameters.add(new GenericParameter<Integer>("ffile",this.trackRequestResult.getPrintFrequency()));
		
		return parameters;
	}

}
