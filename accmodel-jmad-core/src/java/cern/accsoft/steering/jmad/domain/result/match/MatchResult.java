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
