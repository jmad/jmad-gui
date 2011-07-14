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
 * The LMDIF command minimises the sum of squares of the constraint functions using their numerical derivatives:
 * LMDIF,CALLS=integer,TOLERANCE=real; It is the fastest minimisation method available in MAD. The command has two
 * attributes: CALLS: The maximum number of calls to the penalty function (default: 1000). TOLERANCE: The desired
 * tolerance for the minimum (default: 10**(-6)). Example: LMDIF,CALLS=2000,TOLERANCE=1.0E-8;
 * 
 * @author muellerg
 */
public class MatchMethodLmdif extends AbstractMatchMethod {

    private static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.LMDIF;

    @Override
    public AlgorithmType getAlgorithmType() {
        return ALGORITHM_TYPE;
    }
}
