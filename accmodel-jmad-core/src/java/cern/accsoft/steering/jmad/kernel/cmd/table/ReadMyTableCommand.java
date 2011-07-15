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

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the <a href='http://mad.web.cern.ch/mad/control/general.html#readmytable'>'readmytable'</a> command of
 * madx.
 * <p>
 * This command provides the possibility to load into a table with a specific name.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ReadMyTableCommand extends AbstractTableCommand {
    /** the name of the command */
    private static final String CMD_NAME = "readmytable";

    /** the name of the table to read the table into */
    private String tableName;

    public ReadMyTableCommand(File file, String tableName) {
        super(file);
        this.tableName = tableName;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = super.createParameters();
        parameters.add(new GenericParameter<String>("table", tableName));
        return parameters;
    }
}
