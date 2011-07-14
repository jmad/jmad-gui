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
package cern.accsoft.steering.jmad.domain.result.match.methods;

/**
 * The JACOBIAN command minimises the penalty function calculating the Jacobian and solving the linear problem. A QR or
 * LQ decomposition is performed when the system is over or under-determined. Before starting the matching routine two
 * optional transformations (COOL and RANDOM) are performed.
 * JACOBIAN,CALLS=integer,TOLERANCE=real,REPEAT=integer,STRATEGY=integer,COOL= real,BALANCE=real, random=real; The
 * command has the attributes: CALLS: The maximum number of calls to the penalty function (default: 30). TOLERANCE: The
 * desired tolerance for the minimum (default: 10**(-6)). REPEAT: The number of call of the JACOBIAN routine (default:
 * 1). BISEC: Selects the maximum number of iteratation used to determin the step length which reduces the penalty
 * function during the main iteration. A large number (i.e. 6) reduce the probability to diverge from the solution, but
 * increase the one for being trapped in a local minum. STRATEGY: A code for the strategy to be used (default: 3). If
 * STRATEGY=1 the routine resets the values of the variables which exceeds the limits. If STRATEGY=2 the routine print
 * the Jacobian and exit without matching. If STRATEGY=3 the routine disables the variables which exceeds the limits
 * keeping however the number of variables greater or equal to the number of the constraints. COOL, BALANCE: The factors
 * which specify the following transformation: if "balance" >=0 newval=(1-cool)*oldval+cool*(
 * (1-balance)*maxval+balance*minval ) else newval=(1-cool)*oldval+cool* optval where newval is the new value after the
 * transformation, oldval is the previous value, maxval, minval, optval are the maximum value, minimum value, optimal
 * value of the variable specified in the VARY command. RANDOM: The factors which specify the following transformation:
 * newval= (1+ random * rand() ) * oldval where newval is the new value after the transformation, oldval is the previous
 * value, rand() is a stochastic variable with a uniform (-0.5,0.5) distribution. Example:
 * JACOBIAN,CALLS=20,TOLERANCE=1.0E-8,STRATEGY= 3,COOL=0.1,BALANCE=0.5,RANDOM=0.01;
 * 
 * @author muellerg
 */
public class MatchMethodJacobian extends AbstractMatchMethod {

    private static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.JACOBIAN;

    private static final int DEFAULT_CALLS = 30;

    private int repeat = 1;

    /**
     * The default constructor.
     */
    public MatchMethodJacobian() {
        setCalls(DEFAULT_CALLS);
    }

    /**
     * @param repeat adjust the number of call of the JACOBIAN routine
     *            <p>
     *            (default: 1)
     */
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    /**
     * @return the number of calls of the JACOBIAN routine
     */
    public int getRepeat() {
        return repeat;
    }

    @Override
    public AlgorithmType getAlgorithmType() {
        return ALGORITHM_TYPE;
    }
}
