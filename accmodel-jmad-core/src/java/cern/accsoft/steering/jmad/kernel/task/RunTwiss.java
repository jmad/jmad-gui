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
package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequest;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.DeleteCommand;
import cern.accsoft.steering.jmad.kernel.cmd.TwissCommand;

public class RunTwiss extends AbstractResultSelectableTask {

    private static final String SELECT_FLAG_TWISS = "twiss";

    private TwissInitialConditions twiss = null;

    public RunTwiss(TwissInitialConditions twiss) {
        this(twiss, null);
    }

    public RunTwiss(TwissInitialConditions twissInitialConditions, TfsResultRequest resultRequest) {
        super(resultRequest);
        this.twiss = twissInitialConditions;
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        commands.add(new DeleteCommand("twiss"));
        commands.add(new DeleteCommand("summ"));
        commands.addAll(createSelectCommands(SELECT_FLAG_TWISS));    
        commands.add(new TwissCommand(twiss));
        return commands;
    }
}
