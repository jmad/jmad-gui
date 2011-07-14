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
/**
 * 
 */
package cern.accsoft.steering.jmad.kernel.cmd.table;

import java.io.File;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the <a href='http://mad.web.cern.ch/mad/control/general.html#readmytable'>'readmytable'</a> command of madx.
 * <p>
 * This command provides the possibility to load into a table with a specific name.
 * 
 * @author kaifox
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
