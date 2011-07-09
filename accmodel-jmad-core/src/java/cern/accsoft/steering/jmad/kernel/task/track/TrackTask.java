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
