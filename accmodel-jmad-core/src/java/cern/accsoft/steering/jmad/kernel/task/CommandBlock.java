// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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

import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.Command;

/**
 * this block just represents a block of commands which is executed in one go.
 * 
 * @author kfuchsbe
 */
public class CommandBlock extends AbstractTask {
    private final List<Command> commands;

    public CommandBlock(List<Command> commands) {
        super();
        this.commands = commands;
    }

    @Override
    protected List<Command> getCommands() {
        return this.commands;
    }

}
