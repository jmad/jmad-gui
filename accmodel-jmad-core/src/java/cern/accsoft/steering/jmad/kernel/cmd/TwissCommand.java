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

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class TwissCommand extends AbstractCommand {
    private static final String CMD_NAME = "twiss";

    private final TwissInitialConditions twiss;

    public TwissCommand(TwissInitialConditions twiss) {
        this.twiss = twiss;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    /* TODO implement all options! */
    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<Double>("deltap", twiss.getDeltap()));

        /*
         * the initial conditions must not be set, if we want to calc the closed orbit solution
         */
        if (!twiss.isClosedOrbit() && (twiss.getSaveBetaName() == null)) {
            for (MadxTwissVariable var : twiss.getMadxVariables()) {
                Double value = twiss.getValue(var);
                if ((value != null) && (!MadxTwissVariable.DELTAP.equals(var))) {
                    parameters.add(new GenericParameter<Double>(var.getMadxName(), value));
                }
            }
        }

        if (twiss.getSaveBetaName() != null) {
            parameters.add(new GenericParameter<String>("beta0", twiss.getSaveBetaName(), true));
        }

        parameters.add(new GenericParameter<Boolean>("chrom", twiss.isCalcChromaticFunctions()));
        parameters.add(new GenericParameter<Boolean>("centre", twiss.isCalcAtCenter()));
        if (getOutputFile() != null) {
            parameters.add(new GenericParameter<String>("file", getOutputFile().getAbsolutePath(), true));
        }

        return parameters;
    }

}
