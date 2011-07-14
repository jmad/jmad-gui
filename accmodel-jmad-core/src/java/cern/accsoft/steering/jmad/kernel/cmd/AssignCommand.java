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
package cern.accsoft.steering.jmad.kernel.cmd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class AssignCommand extends AbstractCommand {
    private static final String CMD_NAME = "assign";

    /** The file to which the madx output should be directed to */
    private final File file;

    /**
     * Constructor for resetting the echo to terminal
     */
    public AssignCommand() {
        this.file = null;
    }

    /**
     * Constructor to redirect echo for commands value, show and print to a specific file.
     * 
     * @param file the file to which to redirect the output.
     */
    public AssignCommand(File file) {
        this.file = file;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        if (file == null) {
            parameters.add(new GenericParameter<String>("echo", "terminal", false));
        } else {
            parameters.add(new GenericParameter<String>("echo", file.getAbsolutePath(), true));
        }
        return parameters;
    }

}
