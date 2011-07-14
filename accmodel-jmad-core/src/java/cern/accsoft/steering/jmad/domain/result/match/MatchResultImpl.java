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
package cern.accsoft.steering.jmad.domain.result.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.match.output.MadxVaryResult;
import cern.accsoft.steering.jmad.domain.result.match.output.MatchConstraintResult;

public class MatchResultImpl implements MatchResult {

    private boolean successful = false;

    private final double finalPenalty;
    private final List<MatchConstraintResult> conResults;
    private final List<MadxVaryResult> varyResults;

    public MatchResultImpl(double finalPenalty) {
        this.finalPenalty = finalPenalty;
        this.conResults = new ArrayList<MatchConstraintResult>();
        this.varyResults = new ArrayList<MadxVaryResult>();
    }

    public void addConstrainParameterResult(MatchConstraintResult result) {
        this.conResults.add(result);
    }

    @Override
    public List<MatchConstraintResult> getConstraintParameterResults() {
        return this.conResults;
    }

    public void addVaryParameterResult(MadxVaryResult result) {
        this.varyResults.add(result);
    }

    @Override
    public List<MadxVaryResult> getVaryParameterResults() {
        return this.varyResults;
    }

    @Override
    public boolean isSuccessful() {
        return this.successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.MATCH_RESULT;
    }

    @Override
    public double getFinalPenalty() {
        return this.finalPenalty;
    }
}
