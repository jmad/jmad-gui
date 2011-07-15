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
