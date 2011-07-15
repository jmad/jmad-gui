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

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.var.enums.MadxGlobalVariable;

/**
 * @author muellerg
 */
public class MatchConstraintGlobal implements MatchConstraint {

    private static final Logger LOGGER = Logger.getLogger(MatchConstraintGlobal.class);

    private static final List<MadxGlobalVariable> MADX_VARIABLES = Arrays.asList(new MadxGlobalVariable[] { //
            MadxGlobalVariable.Q1, MadxGlobalVariable.Q2, //
                    MadxGlobalVariable.DQ1, MadxGlobalVariable.DQ2, //
            /* MadxGlobalVariable.Q1, MadxGlobalVariable.Q2, */});

    /** the values for the variables */
    private final Map<MadxGlobalVariable, Double> variableValues = new EnumMap<MadxGlobalVariable, Double>(
            MadxGlobalVariable.class);

    public Double getValue(MadxGlobalVariable variable) {
        if (!this.getMadxVariables().contains(variable)) {
            LOGGER.warn("It is not foreseen, that this bean handles the global madx-variable '" + variable.toString()
                    + "'. Therefore there may be no getters and setters for this!");
        }
        return this.variableValues.get(variable);
    }

    private void setValue(MadxGlobalVariable variable, Double value) {
        if (!this.getMadxVariables().contains(variable)) {
            LOGGER.warn("It is not foreseen, that this bean handles the global madx-variable '" + variable.toString()
                    + "'. Therefore there may be no getters and setters for this!");
        }
        this.variableValues.put(variable, value);
    }

    public List<MadxGlobalVariable> getMadxVariables() {
        return MatchConstraintGlobal.MADX_VARIABLES;
    }

    @Override
    public List<String> getReadoutNames() {

        return Arrays.asList(this.variableValues.keySet().toArray(new String[this.variableValues.size()]));
    }

    public Double getQ1() {
        return this.getValue(MadxGlobalVariable.Q1);
    }

    /* short name to use the same as madx */
    public void setQ1(Double q1) { // NOPMD by kaifox on 6/25/10 5:55 PM
        this.setValue(MadxGlobalVariable.Q1, q1);
    }

    public Double getQ2() {
        return this.getValue(MadxGlobalVariable.Q2);
    }

    /* short name to use the same as madx */
    public void setQ2(Double q2) { // NOPMD by kaifox on 6/25/10 5:56 PM
        this.setValue(MadxGlobalVariable.Q2, q2);
    }

    public Double getDq1() {
        return this.getValue(MadxGlobalVariable.DQ1);
    }

    public void setDq1(Double dq1) {
        this.setValue(MadxGlobalVariable.DQ1, dq1);
    }

    public Double getDq2() {
        return this.getValue(MadxGlobalVariable.DQ2);
    }

    public void setDq2(Double dq2) {
        this.setValue(MadxGlobalVariable.DQ2, dq2);
    }

    // public Double getDdq1() {
    // return this.getValue(MadxGlobalVariable.DDQ1);
    // }
    // public void setDdq1(Double ddq1) {
    // this.setValue(MadxGlobalVariable.DDQ1, ddq1);
    // }

    // public void setDdq2(Double ddq2) {
    // this.setValue(MadxGlobalVariable.DDQ2, ddq2);
    // }
    // public Double getDdq2() {
    // return this.getValue(MadxGlobalVariable.DDQ2);
    // }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public Map<String, Double> getParameterSettings() {

        HashMap<String, Double> paraSettings = new HashMap<String, Double>(this.variableValues.size());

        for (MadxGlobalVariable par : this.variableValues.keySet()) {
            paraSettings.put(par.getMadxName(), this.variableValues.get(par));
        }

        return paraSettings;
    }
}
