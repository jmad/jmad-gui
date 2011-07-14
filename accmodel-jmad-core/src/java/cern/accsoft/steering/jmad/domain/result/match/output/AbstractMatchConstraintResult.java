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
package cern.accsoft.steering.jmad.domain.result.match.output;

/**
 * @author muellerg
 */
public abstract class AbstractMatchConstraintResult implements MatchConstraintResult {

    private final String constraint;
    private double targetValue = Double.NaN;
    private final double finalValue;

    public AbstractMatchConstraintResult(String constraint, double finalValue) {
        this.constraint = constraint;
        this.finalValue = finalValue;
    }

    @Override
    public String getConstraint() {
        return this.constraint;
    }

    @Override
    public double getFinalValue() {
        return this.finalValue;
    }

    @Override
    public double getTargetValue() {
        return this.targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }
}
