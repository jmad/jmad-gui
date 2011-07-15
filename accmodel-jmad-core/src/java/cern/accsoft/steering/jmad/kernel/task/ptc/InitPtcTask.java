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

/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.task.ptc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcAlignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcCreateLayoutCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ptc.PtcCreateUniverseCommand;
import cern.accsoft.steering.jmad.kernel.task.AbstractTask;

/**
 * initializes the PTC universe, creates the layout and calls the ptc-align
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class InitPtcTask extends AbstractTask {

    private static final int LAYOUT_DEFAULT_MODEL = 2;
    private static final int LAYOUT_DEFAULT_NST = 1;
    private static final int LAYOUT_DEFAULT_METHOD = 2;

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        commands.add(new PtcCreateUniverseCommand());

        PtcCreateLayoutCommand layoutCmd = new PtcCreateLayoutCommand();
        layoutCmd.setTime(false);
        layoutCmd.setModel(LAYOUT_DEFAULT_MODEL);
        layoutCmd.setMethod(LAYOUT_DEFAULT_METHOD);
        layoutCmd.setNst(LAYOUT_DEFAULT_NST);
        layoutCmd.setExact(true);
        layoutCmd.setResplit(true);
        layoutCmd.setThin(0.0005);
        layoutCmd.setXbend(0.0005);
        commands.add(layoutCmd);

        commands.add(new PtcAlignCommand());
        return commands;
    }

}
