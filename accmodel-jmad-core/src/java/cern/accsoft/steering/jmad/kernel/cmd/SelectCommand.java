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

public class SelectCommand extends AbstractCommand {

    private static final String CMD_NAME = "select";

    private String flag = null;
    private String range = null;
    private String elementClass = null;
    private String pattern = null;
    private String column = null;
    private Boolean full = null;
    private Boolean clear = null;

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<String>("flag", flag));
        parameters.add(new GenericParameter<String>("range", range));
        parameters.add(new GenericParameter<String>("class", elementClass));
        parameters.add(new GenericParameter<String>("pattern", pattern, true));
        parameters.add(new GenericParameter<String>("column", column));
        parameters.add(new GenericParameter<Boolean>("full", full));
        parameters.add(new GenericParameter<Boolean>("clear", clear));

        return parameters;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getElementClass() {
        return elementClass;
    }

    public void setElementClass(String elementClass) {
        this.elementClass = elementClass;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public Boolean getClear() {
        return clear;
    }

    public void setClear(Boolean clear) {
        this.clear = clear;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

}
