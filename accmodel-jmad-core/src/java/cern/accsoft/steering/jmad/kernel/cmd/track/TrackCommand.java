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

import cern.accsoft.steering.jmad.domain.track.TrackInitialCondition;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * command TRACK, onepass, deltap= double, dump,...;
 * 
 * @author xbuffat
 */

public class TrackCommand extends AbstractCommand {

    private static final String CMD_NAME = "track";

    private final TrackInitialCondition trackInitialCondition;

    public TrackCommand(TrackInitialCondition init) {
        this.trackInitialCondition = init;
    }

    @Override
    public String getName() {
        return TrackCommand.CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<Double>("deltap", this.trackInitialCondition.getDeltaP()));
        parameters.add(new GenericParameter<Boolean>("onepass", this.trackInitialCondition.isOnePass()));
        parameters.add(new GenericParameter<Boolean>("recloss", this.trackInitialCondition.isCreateLossParticleFile()));
        parameters.add(new GenericParameter<Boolean>("onetable", this.trackInitialCondition.isOneTable()));
        parameters.add(new GenericParameter<Boolean>("quantum", this.trackInitialCondition.isQuantumExcited()));
        parameters.add(new GenericParameter<Boolean>("damp", this.trackInitialCondition.isSynchrotronDamped()));
        parameters.add(new GenericParameter<Boolean>("dump", this.trackInitialCondition.isWriteAtEachTurn()));

        return parameters;
    }
}
