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
 * @author kaifox
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
