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
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class UseCommand extends AbstractCommand {
    private static final String CMD_NAME = "use";

    private static final String PARAM_NAME_PERIOD = "period";
    private static final String PARAM_NAME_RANGE = "range";

    /* the sequence to use */
    private String period = null;

    /* the range to use */
    private String range = null;

    public UseCommand(String period) {
        this(period, null);
    }

    public UseCommand(String period, MadxRange range) {
        this.period = period;
        if (range == null) {
            this.range = null;
        } else {
            this.range = range.getMadxString();
        }
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<String>(PARAM_NAME_PERIOD, period));
        parameters.add(new GenericParameter<String>(PARAM_NAME_RANGE, range));

        return parameters;
    }

}
