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

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * Command write,table=tableName,file=fileName;
 * 
 * @author xbuffat
 */

public class WriteCommand extends AbstractCommand {

    private static final String CMD_NAME = "write";

    private String tableName;
    private String file;

    public WriteCommand(String tableName) {
        this(tableName, null);
    }

    public WriteCommand(String tableName, String file) {
        this.tableName = tableName;
        this.file = file;
    }

    @Override
    public String getName() {
        return WriteCommand.CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<String>("table", this.tableName, true));
        if (file != null) {
            parameters.add(new GenericParameter<String>("file", this.file, true));
        }
        return parameters;
    }

}
