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
