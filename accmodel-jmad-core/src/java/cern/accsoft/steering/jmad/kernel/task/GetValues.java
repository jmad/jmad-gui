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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.kernel.cmd.AssignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.SetFormatCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ValueCommand;

public class GetValues extends AbstractTask {

    private final Collection<String> valueNames;

    public GetValues(Collection<String> valueNames) {
        this.valueNames = valueNames;
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();
        commands.add(new SetFormatCommand(SetFormatCommand.DEFAULT_FORMAT_FLOAT));
        commands.add(new AssignCommand(getOutputFile()));
        commands.add(new ValueCommand(valueNames));
        commands.add(new AssignCommand());
        return commands;
    }

    @Override
    public ResultType getResultType() {
        if ((valueNames == null) || (valueNames.isEmpty()) || (getOutputFile() == null)) {
            return super.getResultType();
        } else {
            return ResultType.VALUES_RESULT;
        }
    }
}
