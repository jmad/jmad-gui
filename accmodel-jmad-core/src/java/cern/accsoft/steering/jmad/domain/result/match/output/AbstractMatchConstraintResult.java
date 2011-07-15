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
