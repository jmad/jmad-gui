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
 * the most general form of a match method
 * 
 * @author muellerg
 */
public abstract class AbstractMatchMethod implements MatchMethod {

    private static final int DEFAULT_CALLS = 1000;

    private static final double DEFAULT_TOLERANCE = 1e-8;

    /** the desired tolerance for the minimum of the matching command */
    private double tolerance = DEFAULT_TOLERANCE;
    private int calls = DEFAULT_CALLS;

    @Override
    public double getTolerance() {
        return this.tolerance;
    }

    /**
     * @param tolerance the tolerance to set
     */
    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    /**
     * Adjust the maximum number of calls to the penalty function
     * 
     * @param calls number of calls to process max.
     */
    public void setCalls(int calls) {
        this.calls = calls;
    }

    public int getCalls() {
        return calls;
    }

}
