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
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * The command to set the output format of MadX: <a href="http://mad.web.cern.ch/mad/Introduction/set.html">SET</a>.
 * Unlike the MadX version, which supports up to three format settings at the same time, we only support setting one
 * format at a time here.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class SetFormatCommand extends AbstractCommand {
    private static final String CMD_NAME = "set";

    /** default format for integers */
    public static final String DEFAULT_FORMAT_INTEGER = "10d";

    /** the default output format for doubles */
    public static final String DEFAULT_FORMAT_FLOAT = "18.10g";

    /** the default output format for strings */
    public static final String DEFAULE_FORMAT_STRING = "-18s";

    /** the format to set */
    private final String format;

    /**
     * Set-command, to set output to the given format
     * 
     * @param format the format to set
     */
    public SetFormatCommand(String format) {
        this.format = format;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new GenericParameter<String>("format", this.format, true));
        return parameters;
    }

}
