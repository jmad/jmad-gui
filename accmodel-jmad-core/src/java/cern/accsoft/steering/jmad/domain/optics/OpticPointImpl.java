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

package cern.accsoft.steering.jmad.domain.optics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.util.bean.NamedBean;

public class OpticPointImpl implements NamedBean, OpticPoint {

    /** the logger for the class */
    protected static final Logger LOGGER = Logger.getLogger(OpticPointImpl.class);

    /** The name of this point (element name) */
    private final String name;

    /** all the madx-variables that are available at an optics-point */
    public static final List<MadxTwissVariable> MADX_VARIABLES = Arrays.asList(new MadxTwissVariable[] { //
            MadxTwissVariable.S, //
                    MadxTwissVariable.BETX, MadxTwissVariable.BETY, //
                    MadxTwissVariable.ALFX, MadxTwissVariable.ALFY, //
                    MadxTwissVariable.MUX, MadxTwissVariable.MUY, //
                    MadxTwissVariable.DX, MadxTwissVariable.DY, //
                    MadxTwissVariable.DPX, MadxTwissVariable.DPY, //
                    MadxTwissVariable.X, MadxTwissVariable.Y, //
                    MadxTwissVariable.PX, MadxTwissVariable.PY, //
                    MadxTwissVariable.DDX, MadxTwissVariable.DDY, //
                    MadxTwissVariable.DDPX, MadxTwissVariable.DDPY });

    /** the values for the variables */
    protected Map<MadxTwissVariable, Double> variableValues = new HashMap<MadxTwissVariable, Double>();

    public OpticPointImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getValue(MadxTwissVariable variable) {
        if (!this.getMadxVariables().contains(variable)) {
            LOGGER.warn("It is not foreseen, that this bean handles the madx-variable '" + variable.toString()
                    + "'. Therefore there may be no getters and setters for this!");
        }
        return this.variableValues.get(variable);
    }

    public void setValue(MadxTwissVariable variable, Double value) {
        if (!this.getMadxVariables().contains(variable)) {
            LOGGER.warn("It is not foreseen, that this bean handles the madx-variable '" + variable.toString()
                    + "'. Therefore there may be no getters and setters for this!");
        }
        this.variableValues.put(variable, value);
        firePropertyChanged();
    }

    @Override
    public Double getValue(JMadTwissVariable variable, JMadPlane plane) {
        return this.getValue(variable.getMadxTwissVariable(plane));
    }

    @Override
    public Double getBetx() {
        return getValue(MadxTwissVariable.BETX);
    }

    @Override
    public Double getBety() {
        return getValue(MadxTwissVariable.BETY);
    }

    @Override
    public Double getMux() {
        return getValue(MadxTwissVariable.MUX);
    }

    @Override
    public Double getMuy() {
        return getValue(MadxTwissVariable.MUY);
    }

    @Override
    public Double getDx() {
        return getValue(MadxTwissVariable.DX);
    }

    @Override
    public Double getDy() {
        return getValue(MadxTwissVariable.DY);
    }

    @Override
    public Double getAlfx() {
        return getValue(MadxTwissVariable.ALFX);
    }

    @Override
    public Double getAlfy() {
        return getValue(MadxTwissVariable.ALFY);
    }

    @Override
    public Double getDdx() {
        return getValue(MadxTwissVariable.DDX);
    }

    @Override
    public Double getDpx() {
        return getValue(MadxTwissVariable.DPX);
    }

    @Override
    public Double getDpy() {
        return getValue(MadxTwissVariable.DPY);
    }

    @Override
    public Double getDdy() {
        return getValue(MadxTwissVariable.DDY);
    }

    @Override
    public Double getDdpx() {
        return getValue(MadxTwissVariable.DDPX);
    }

    @Override
    public Double getDdpy() {
        return getValue(MadxTwissVariable.DDPY);
    }

    @Override
    public Double getX() {
        return getValue(MadxTwissVariable.X);
    }

    @Override
    public Double getPx() {
        return getValue(MadxTwissVariable.PX);
    }

    @Override
    public Double getY() {
        return getValue(MadxTwissVariable.Y);
    }

    @Override
    public Double getPy() {
        return getValue(MadxTwissVariable.PY);
    }

    public List<MadxTwissVariable> getMadxVariables() {
        return OpticPointImpl.MADX_VARIABLES;
    }

    /**
     * this method may be overridden by subclasses in order to notify their listeners. For the moment we do nothing
     * here!
     */
    protected void firePropertyChanged() {
        /* Do nothing. May be overridden by subclass */
    }

    @Override
    public Double getPosition() {
        return getValue(MadxTwissVariable.S);
    }
}
