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
package cern.accsoft.steering.jmad.domain.result.match.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

/**
 * @author muellerg
 */
public class MatchConstraintLocal implements MatchConstraint {

    private static final Logger LOGGER = Logger.getLogger(MatchConstraintLocal.class);

    private static final List<MadxTwissVariable> MADX_VARIABLES = Arrays.asList(new MadxTwissVariable[] { //
            MadxTwissVariable.BETX, MadxTwissVariable.BETY, //
                    MadxTwissVariable.ALFX, MadxTwissVariable.ALFY, //
                    MadxTwissVariable.MUX, MadxTwissVariable.MUY, //
                    MadxTwissVariable.DX, MadxTwissVariable.DY, //
                    MadxTwissVariable.DDX, MadxTwissVariable.DDY, //
                    MadxTwissVariable.DPX, MadxTwissVariable.DPY, //
                    MadxTwissVariable.DDPX, MadxTwissVariable.DDPY, //
                    MadxTwissVariable.X, MadxTwissVariable.Y, //
                    MadxTwissVariable.PX, MadxTwissVariable.PY });

    /** the values for the variables */
    private final Map<MadxTwissVariable, Double> variableValues = new EnumMap<MadxTwissVariable, Double>(
            MadxTwissVariable.class);

    /** Range for the Constraint initialized to whole Sequence!!! */
    private final MadxRange madxRange;

    // TODO Upgrade to use relations to, for now only equal until somebody needs
    // something else ;)
    // public static enum ConstraintRelation {
    // EQUALS("=");
    //
    // private ConstraintRelation(String madxChar) {
    // this.madxChar = madxChar;
    // }
    //
    // private String madxChar;
    //
    // public String getMadxChar() {
    // return madxChar;
    // }
    //
    // }

    public MatchConstraintLocal(MadxRange madxRange) {
        this.madxRange = madxRange;
    }

    public Double getValue(MadxTwissVariable variable) {
        if (!this.getMadxVariables().contains(variable)) {
            LOGGER.warn("It is not foreseen, that this bean handles the madx-variable '" + variable.toString()
                    + "'. Therefore there may be no getters and setters for this!");
        }
        return this.variableValues.get(variable);
    }

    private void setValue(MadxTwissVariable variable, Double value) {
        if (!this.getMadxVariables().contains(variable)) {
            LOGGER.warn("It is not foreseen, that this bean handles the madx-variable '" + variable.toString()
                    + "'. Therefore there may be no getters and setters for this!");
        }
        this.variableValues.put(variable, value);
    }

    public Double getBetx() {
        return getValue(MadxTwissVariable.BETX);
    }

    public void setBetx(Double betx) {
        setValue(MadxTwissVariable.BETX, betx);
    }

    public Double getBety() {
        return getValue(MadxTwissVariable.BETY);
    }

    public void setBety(Double bety) {
        setValue(MadxTwissVariable.BETY, bety);
    }

    public Double getMux() {
        return getValue(MadxTwissVariable.MUX);
    }

    public void setMux(Double mux) {
        setValue(MadxTwissVariable.MUX, mux);
    }

    public Double getMuy() {
        return getValue(MadxTwissVariable.MUY);
    }

    public void setMuy(Double muy) {
        setValue(MadxTwissVariable.MUY, muy);
    }

    public Double getDx() {
        return getValue(MadxTwissVariable.DX);
    }

    /* short name to use the same as madx */
    public void setDx(Double dx) { // NOPMD by kaifox on 6/25/10 5:56 PM
        setValue(MadxTwissVariable.DX, dx);
    }

    public Double getDy() {
        return getValue(MadxTwissVariable.DY);
    }

    /* short name to use the same as madx */
    public void setDy(Double dy) { // NOPMD by kaifox on 6/25/10 5:57 PM
        setValue(MadxTwissVariable.DY, dy);
    }

    public Double getAlfx() {
        return getValue(MadxTwissVariable.ALFX);
    }

    public void setAlfx(Double alfx) {
        setValue(MadxTwissVariable.ALFX, alfx);
    }

    public Double getAlfy() {
        return getValue(MadxTwissVariable.ALFY);
    }

    public void setAlfy(Double alfy) {
        setValue(MadxTwissVariable.ALFY, alfy);
    }

    public Double getDdx() {
        return getValue(MadxTwissVariable.DDX);
    }

    public void setDdx(Double ddx) {
        setValue(MadxTwissVariable.DDX, ddx);
    }

    public Double getDpx() {
        return getValue(MadxTwissVariable.DPX);
    }

    public void setDpx(Double dpx) {
        setValue(MadxTwissVariable.DPX, dpx);
    }

    public Double getDpy() {
        return getValue(MadxTwissVariable.DPY);
    }

    public void setDpy(Double dpy) {
        setValue(MadxTwissVariable.DPY, dpy);
    }

    public Double getDdy() {
        return getValue(MadxTwissVariable.DDY);
    }

    public void setDdy(Double ddy) {
        setValue(MadxTwissVariable.DDY, ddy);
    }

    public Double getDdpx() {
        return getValue(MadxTwissVariable.DDPX);
    }

    public void setDdpx(Double ddpx) {
        setValue(MadxTwissVariable.DDPX, ddpx);
    }

    public Double getDdpy() {
        return getValue(MadxTwissVariable.DDPY);
    }

    public void setDdpy(Double ddpy) {
        setValue(MadxTwissVariable.DDPY, ddpy);
    }

    public Double getX() {
        return getValue(MadxTwissVariable.X);
    }

    /* short name to use the same as madx */
    public void setX(Double x) { // NOPMD by kaifox on 6/25/10 5:57 PM
        setValue(MadxTwissVariable.X, x);
    }

    public Double getPx() {
        return getValue(MadxTwissVariable.PX);
    }

    /* short name to use the same as madx */
    public void setPx(Double px) { // NOPMD by kaifox on 6/25/10 5:57 PM
        setValue(MadxTwissVariable.PX, px);
    }

    public Double getY() {
        return getValue(MadxTwissVariable.Y);
    }

    /* short name to use the same as madx */
    public void setY(Double y) { // NOPMD by kaifox on 6/25/10 5:57 PM
        setValue(MadxTwissVariable.Y, y);
    }

    public Double getPy() {
        return getValue(MadxTwissVariable.PY);
    }

    /* PMD: short variable name to be consistent with MadX */
    public void setPy(Double py) { // NOPMD by kaifox on 10/6/10 6:42 PM
        setValue(MadxTwissVariable.PY, py);
    }

    public List<MadxTwissVariable> getMadxVariables() {
        return MatchConstraintLocal.MADX_VARIABLES;
    }

    public MadxRange getMadxRange() {
        return madxRange;
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public List<String> getReadoutNames() {

        ArrayList<String> readOutNames = new ArrayList<String>();

        String[] opticVars = new String[this.variableValues.size()];
        int varCount = 0;
        for (MadxTwissVariable var : this.variableValues.keySet()) {
            opticVars[varCount++] = var.getMadxName();
        }

        if (!this.madxRange.isElement()) {
            String[] firstLast = this.madxRange.getMadxString().split(MadxRange.ELEMENT_SEPARATOR);

            // For now if a Range is defined for a local Constraint read Values
            // for first and last Element
            for (String attr : opticVars) {
                readOutNames.add(firstLast[0] + Element.ATTR_SEPARATOR + attr);
                readOutNames.add(firstLast[1] + Element.ATTR_SEPARATOR + attr);
            }
        } else {
            String elem = this.madxRange.getMadxString();

            for (String attr : opticVars) {
                readOutNames.add(elem + Element.ATTR_SEPARATOR + attr);
            }
        }

        return readOutNames;
    }

    @Override
    public Map<String, Double> getParameterSettings() {

        HashMap<String, Double> paraSettings = new HashMap<String, Double>(this.variableValues.size());

        for (MadxTwissVariable par : this.variableValues.keySet()) {
            paraSettings.put(par.getMadxName(), this.variableValues.get(par));
        }

        return paraSettings;
    }

}
