// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        for (RelativeParticleCoordinate coord : this.getTrackResultRequest().getRelativeParticleDistribution()) {
            commands.add(new TrackStartCommand(coord));
        }
        commands.add(new TrackRunCommand(this.getTrackResultRequest()));
        commands.add(new TrackEndCommand());
        if (this.getOutputFile() != null) {
            commands.add(new WriteCommand("trackone", this.getOutputFile().getAbsolutePath()));
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
