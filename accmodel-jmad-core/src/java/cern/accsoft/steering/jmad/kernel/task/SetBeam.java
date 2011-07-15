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

package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.kernel.cmd.BeamCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.ResbeamCommand;

public class SetBeam extends AbstractTask {
    private final Beam beam;

    public SetBeam(Beam beam) {
        this.beam = beam;
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();

        ResbeamCommand resbeamCommand = new ResbeamCommand(beam);
        commands.add(resbeamCommand);

        BeamCommand beamCommand = new BeamCommand(beam);
        commands.add(beamCommand);

        return commands;
    }

}
