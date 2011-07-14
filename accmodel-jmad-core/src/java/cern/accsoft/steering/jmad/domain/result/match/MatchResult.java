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
/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match;

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.result.match.input.MadxVaryParameter;
import cern.accsoft.steering.jmad.domain.result.match.input.MatchConstraint;
import cern.accsoft.steering.jmad.domain.result.match.output.MadxVaryResult;
import cern.accsoft.steering.jmad.domain.result.match.output.MatchConstraintResult;

/**
 * This interface represents the result of a matching task in madx
 * 
 * @author muellerg
 */
public interface MatchResult extends Result {

    /**
     * @return <code>true</code> if the matching Job reached the desired tolerance
     */
    public abstract boolean isSuccessful();

    /**
     * @return the Value of the Final Penalty Function (TAR) which describes the reached tolerance during matching
     */
    public abstract double getFinalPenalty();

    /**
     * @return the resulting Values ({@link MadxVaryResult}s) of the Matching Job for the defined
     *         {@link MadxVaryParameter}s
     */
    public abstract List<MadxVaryResult> getVaryParameterResults();

    /**
     * @return the final Values ({@link MatchConstraintResult}s) of the Matching Job for the defined
     *         {@link MatchConstraint}s
     */
    public abstract List<MatchConstraintResult> getConstraintParameterResults();
}
