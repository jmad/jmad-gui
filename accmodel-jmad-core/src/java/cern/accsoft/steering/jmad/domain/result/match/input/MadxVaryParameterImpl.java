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
package cern.accsoft.steering.jmad.domain.result.match.input;

import cern.accsoft.steering.jmad.domain.knob.MadxParameter;

/**
 * this
 * 
 * @author muellerg
 */
public class MadxVaryParameterImpl implements MadxVaryParameter {

    /** the madx knob to vary */
    private final MadxParameter parameter;

    /** the step for the variation */
    private Double step = null;

    public void setStep(Double step) {
        this.step = step;
    }

    public void setLower(Double lower) {
        this.lower = lower;
    }

    public void setUpper(Double upper) {
        this.upper = upper;
    }

    /** the lower limit */
    private Double lower = null;

    /** the upper limit */
    private Double upper = null;

    public MadxVaryParameterImpl(MadxParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public String getName() {
        return this.parameter.getMadxName();
    }

    @Override
    public Double getLower() {
        return this.lower;
    }

    @Override
    public Double getStep() {
        return this.step;
    }

    @Override
    public Double getUpper() {
        return this.upper;
    }

    @Override
    public MadxParameter getMadxParameter() {
        return this.parameter;
    }

}
