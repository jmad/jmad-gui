// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
 * @author kfuchsbe
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
