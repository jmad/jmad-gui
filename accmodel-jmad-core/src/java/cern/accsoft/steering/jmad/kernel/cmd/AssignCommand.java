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
