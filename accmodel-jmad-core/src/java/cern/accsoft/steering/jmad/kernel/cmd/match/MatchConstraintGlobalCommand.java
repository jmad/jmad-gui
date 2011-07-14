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
import java.util.Map;
import java.util.Map.Entry;

import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraintGlobal;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchConstraintGlobalCommand extends AbstractCommand {
    private static final String CMD_NAME = "global";

    @Override
    public String getName() {
        return CMD_NAME;
    }

    private final MatchConstraintGlobal actConstraint;
    private final String actSequ;

    public MatchConstraintGlobalCommand(MatchConstraintGlobal actConstraint, String sequenceName) {
        super();
        this.actConstraint = actConstraint;
        this.actSequ = sequenceName;
    }

    @Override
    public List<Parameter> getParameters() {

        List<Parameter> params = new ArrayList<Parameter>();

        params.add(new GenericParameter<String>("sequence", this.actSequ));

        Map<String, Double> paramSettings = this.actConstraint.getParameterSettings();

        for (Entry<String, Double> entry : paramSettings.entrySet()) {
            params.add(new GenericParameter<Double>(entry.getKey(), entry.getValue()));
        }

        return params;
    }

}
