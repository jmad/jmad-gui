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
package cern.accsoft.steering.jmad.kernel.task.track;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.track.TrackResultRequest;
import cern.accsoft.steering.jmad.domain.track.RelativeParticleCoordinate;
import cern.accsoft.steering.jmad.domain.track.TrackInitialCondition;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.WriteCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.TrackCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.TrackEndCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.TrackRunCommand;
import cern.accsoft.steering.jmad.kernel.cmd.track.TrackStartCommand;
import cern.accsoft.steering.jmad.kernel.task.AbstractTask;

/**
 * Execute a track task and if an output file is defined write the result to it.
 * 
 * @author xbuffat
 *
 */

public class TrackTask extends AbstractTask {

	private final TrackInitialCondition trackInitialCondition;
	private final TrackResultRequest trackResultRequest;
	
	public TrackTask(TrackInitialCondition init, TrackResultRequest request) {
		this.trackInitialCondition = init;
		this.trackResultRequest = request;
	}
	
	@Override
	protected List<Command> getCommands() {
		List<Command> commands = new ArrayList<Command>();
		commands.add(new TrackCommand(this.getTrackInitialCondition()));
		for(RelativeParticleCoordinate coord : this.getTrackResultRequest().getRelativeParticleDistribution())
		{
			commands.add(new TrackStartCommand(coord));
		}
		commands.add(new TrackRunCommand(this.getTrackResultRequest()));
		commands.add(new TrackEndCommand());
		if(this.getOutputFile() != null)
		{
			commands.add(new WriteCommand("trackone",this.getOutputFile().getAbsolutePath()));
		}
		return commands;
	}
	
    @Override
    public ResultType getResultType() {
        return ResultType.TRACK_RESULT;
    }
	

	public TrackInitialCondition getTrackInitialCondition() {
		return trackInitialCondition;
	}

	public TrackResultRequest getTrackResultRequest() {
		return trackResultRequest;
	}

}
