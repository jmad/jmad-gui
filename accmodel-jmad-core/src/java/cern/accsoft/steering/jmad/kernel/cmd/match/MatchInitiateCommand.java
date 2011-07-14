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

import cern.accsoft.steering.jmad.domain.result.match.MatchResultRequest;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchInitiateCommand extends AbstractCommand {
    private static final String CMD_NAME = "match";

    private final MatchResultRequest request;

    public MatchInitiateCommand(MatchResultRequest actRequ) {
        this.request = actRequ;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<String>("sequence", this.request.getSequenceName()));
        if (this.request.getSaveBetaName() == null) {
            if (this.request.getInitialOpticsValues() != null) {
                TwissInitialConditions twiss = this.request.getInitialOpticsValues();
                for (MadxTwissVariable var : twiss.getMadxVariables()) {
                    parameters.add(new GenericParameter<Double>(var.getMadxName(), twiss.getValue(var)));

                }

            }
        } else {
            parameters.add(new GenericParameter<String>("beta0", this.request.getSaveBetaName(), true));
        }

        return parameters;
    }
}
