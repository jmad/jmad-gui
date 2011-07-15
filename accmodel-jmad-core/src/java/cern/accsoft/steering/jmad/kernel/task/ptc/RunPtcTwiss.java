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

package cern.accsoft.steering.jmad.kernel.task.ptc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequest;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcTwissCommand;
import cern.accsoft.steering.jmad.kernel.task.AbstractResultSelectableTask;

/**
 * Represents a task to be executed in the kernel, that prepares the environment for a ptc twiss and exevutes the
 * ptc_twiss command
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class RunPtcTwiss extends AbstractResultSelectableTask {

    private static final String SELECT_FLAG_PTC_TWISS = "ptc_twiss";

    private TwissInitialConditions twiss = null;

    /**
     * The minimal constructor for the task.
     * 
     * @param twiss the initial conditions to use for the twiss.
     */
    public RunPtcTwiss(TwissInitialConditions twiss) {
        this(twiss, null);
    }

    /**
     * A constructor, which additionaly allows to define the requested result variables.
     * 
     * @param twiss the initial conditions to use for the twiss
     * @param resultRequest the object containing needed results
     */
    public RunPtcTwiss(TwissInitialConditions twiss, TfsResultRequest resultRequest) {
        super(resultRequest);
        this.twiss = twiss;
    }

    @Override
    protected final List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        commands.addAll(createSelectCommands(SELECT_FLAG_PTC_TWISS));
        commands.add(new PtcTwissCommand(twiss));
        return commands;
    }
}
