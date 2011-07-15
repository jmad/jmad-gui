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

        if (this.trackRequestResult.isApertureLimited()) {
            LOGGER.warn("maxaper not available yet, no aperture defined");
            // parameters.add(new
            // GenericParameter<Double[]>("maxaper",this.trackRequestResult.getApertureLimitation()));
        }
        parameters.add(new GenericParameter<Integer>("turns", this.trackRequestResult.getTurns()));
        parameters.add(new GenericParameter<Integer>("ffile", this.trackRequestResult.getPrintFrequency()));

        return parameters;
    }

}
