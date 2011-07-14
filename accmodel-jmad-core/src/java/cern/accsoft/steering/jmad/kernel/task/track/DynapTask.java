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
package cern.accsoft.steering.jmad.kernel.task.track;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.track.DynapResultRequest;
import cern.accsoft.steering.jmad.domain.track.RelativeParticleCoordinate;
import cern.accsoft.steering.jmad.domain.track.TrackInitialCondition;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.WriteCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.DynapCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.TrackCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.TrackEndCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.TrackStartCommand;
import cern.accsoft.steering.jmad.kernel.task.AbstractTask;

/**
 * 
 * Execute a Dynap task and if an outputFile is define write the result to it.
 * Note : if dynapResultRequest.isFastTune() only the tunes of the particles are written
 * 
 * @author xbuffat
 *
 */

public class DynapTask extends AbstractTask {

	private final TrackInitialCondition trackInitialCondition;
	private final DynapResultRequest dynapResultRequest;
	
	public DynapTask(TrackInitialCondition init,DynapResultRequest request) {
		this.trackInitialCondition = init;
		this.dynapResultRequest = request;
	}
	
	@Override
	protected List<Command> getCommands() {
		List<Command> commands = new ArrayList<Command>();
		commands.add(new TrackCommand(this.getTrackInitialCondition()));
		for(RelativeParticleCoordinate coord : this.getDynapResultRequest().getRelativeParticleDistribution())
		{
			commands.add(new TrackStartCommand(coord));
		}
		commands.add(new DynapCommand(this.getDynapResultRequest()));
		commands.add(new TrackEndCommand());
		
		if(this.getOutputFile() != null)
		{
			if(this.getDynapResultRequest().isFastTune())
			{
				commands.add(new WriteCommand("dynaptune",this.getOutputFile().getAbsolutePath()));
			}
			else
			{
				commands.add(new WriteCommand("dynap",this.getOutputFile().getAbsolutePath()));
			}
		}
		
		return commands;
	}
	
	public DynapResultRequest getDynapResultRequest() {
		return dynapResultRequest;
	}
	public TrackInitialCondition getTrackInitialCondition() {
		return trackInitialCondition;
	}
	
    @Override
    public ResultType getResultType() {
        return ResultType.DYNAP_RESULT;
    }
	
}
