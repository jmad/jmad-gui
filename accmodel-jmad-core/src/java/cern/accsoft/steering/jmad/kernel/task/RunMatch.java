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
package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.match.MatchResultRequest;
import cern.accsoft.steering.jmad.domain.result.match.input.MadxVaryParameter;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraint;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraintGlobal;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraintLocal;
import cern.accsoft.steering.jmad.domain.result.match.methods.AbstractMatchMethod;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethod;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodLmdif;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodMigrad;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodSimplex;
import cern.accsoft.steering.jmad.io.MatchOutputParser.MatchingOutputTag;
import cern.accsoft.steering.jmad.kernel.cmd.AssignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.PrintCommand;
import cern.accsoft.steering.jmad.kernel.cmd.SetFormatCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ValueCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchConstraintGlobalCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchConstraintLocalCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchEndCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchInitiateCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchMethodJacobianCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchMethodLmdifCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchMethodMigradCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchMethodSimplexCommand;
import cern.accsoft.steering.jmad.kernel.cmd.match.MatchVaryParameterCommand;

public class RunMatch extends AbstractTask {

    public static final double FINAL_PENALTY_FACTOR = 1.0e25;

    private final MatchResultRequest request;

    public RunMatch(MatchResultRequest newReq) {
        this.request = newReq;
    }

    @Override
    public ResultType getResultType() {
        if (this.request == null) {
            return ResultType.NO_RESULT;
        } else {
            return ResultType.MATCH_RESULT;
        }
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();

        // Initial Match Command (eventually SAVEBETA/InitialTwiss afterwards)
        commands.add(new MatchInitiateCommand(this.request));

        // Add Constraint Commands
        for (MatchConstraint constraint : this.request.getMatchConstraints()) {
            if (constraint.isGlobal()) {
                commands.add(new MatchConstraintGlobalCommand((MatchConstraintGlobal) constraint, this.request
                        .getSequenceName()));
            } else {
                commands.add(new MatchConstraintLocalCommand((MatchConstraintLocal) constraint, this.request
                        .getSequenceName()));
            }

        }

        // List of VaryParameters to read after the Matching Job
        List<String> varyNames = new ArrayList<String>();

        // Add list of VaryParameters...
        for (MadxVaryParameter vParam : this.request.getMadxVaryParameters()) {
            commands.add(new MatchVaryParameterCommand(vParam));
            varyNames.add(vParam.getName());
        }

        // Specify Matching Method...
        MatchMethod actMethod = this.request.getMatchMethod();
        switch (actMethod.getAlgorithmType()) {
        case LMDIF:
            commands.add(new MatchMethodLmdifCommand((MatchMethodLmdif) actMethod));
            break;
        case JACOBIAN:
            commands.add(new MatchMethodJacobianCommand((AbstractMatchMethod) actMethod));
            break;
        case SIMPLEX:
            commands.add(new MatchMethodSimplexCommand((MatchMethodSimplex) actMethod));
            break;
        case MIGRAD:
            commands.add(new MatchMethodMigradCommand((MatchMethodMigrad) actMethod));
            break;
        case NONE:
            // TODO think about ErrorHandling...
            // for now empty CmdArray --> no action
            return new ArrayList<Command>();
        }

        // Append endMatch Command to CmdBlock/JMadTask
        commands.add(new MatchEndCommand());

        // Append ReadOut Commands for Final Penalty and VaryParameter final
        // Values
        commands.add(new SetFormatCommand(SetFormatCommand.DEFAULT_FORMAT_FLOAT));
        commands.add(new AssignCommand(getOutputFile()));

        // Retrieve Final Penalty Function Value, value of 'TAR' multiplied by
        // finalPenaltyFactor to even retrieve small numbers that might be
        // omitted by the Madx SetFormat command...
        commands.add(new PrintCommand(MatchingOutputTag.FINAL_PENALTY.toString()));
        commands.add(new ValueCommand(Collections.singletonList(FINAL_PENALTY_FACTOR + "*"
                + MatchingOutputTag.FINAL_PENALTY.toString())));

        // Issue command to read VaryParameters
        commands.add(new PrintCommand(MatchingOutputTag.VARY_PARAMETERS.toString()));
        commands.add(new ValueCommand(varyNames));

        commands.add(new AssignCommand());

        return commands;
    }
}
