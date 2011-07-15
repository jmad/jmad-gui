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
package cern.accsoft.steering.jmad.kernel.cmd.table;

import java.io.File;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the readtable command of madx
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ReadTableCommand extends AbstractTableCommand {

    /** the name of the command */
    private static final String CMD_NAME = "readtable";

    /**
     * the default constructor with the file
     * 
     * @param file the file from which to load the table
     */
    public ReadTableCommand(File file) {
        super(file);
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        return super.createParameters();
    }

}
