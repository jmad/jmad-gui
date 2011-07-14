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
package cern.accsoft.steering.jmad.kernel.cmd.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.input.MadxVaryParameter;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchVaryParameterCommand extends AbstractCommand {
    private static final String CMD_NAME = "vary";

    @Override
    public String getName() {
        return CMD_NAME;
    }

    private final MadxVaryParameter actVaryParameter;

    public MatchVaryParameterCommand(MadxVaryParameter varyParameter) {
        super();
        this.actVaryParameter = varyParameter;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> params = new ArrayList<Parameter>();

        params.add(new GenericParameter<String>("name", this.actVaryParameter.getName()));
        params.add(new GenericParameter<Double>("step", this.actVaryParameter.getStep()));
        params.add(new GenericParameter<Double>("lower", this.actVaryParameter.getLower()));
        params.add(new GenericParameter<Double>("upper", this.actVaryParameter.getUpper()));
        return params;
    }
}
