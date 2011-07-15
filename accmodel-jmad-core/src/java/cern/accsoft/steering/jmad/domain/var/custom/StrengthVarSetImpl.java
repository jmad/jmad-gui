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
package cern.accsoft.steering.jmad.domain.var.custom;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.knob.strength.Strength;

/**
 * The default implementation of a {@link StrengthVarSet}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class StrengthVarSetImpl implements StrengthVarSet {

    /** the strengths */
    private final Map<String, Strength> strengths = new LinkedHashMap<String, Strength>();

    /** the variables */
    private final Map<String, CustomVariable> variables = new LinkedHashMap<String, CustomVariable>();

    @Override
    public void addAllStrengths(Collection<Strength> newStrengths) {
        for (Strength strength : newStrengths) {
            String key = unifyKey(strength.getKey());
            remove(key);
            this.strengths.put(key, strength);
        }
    }

    @Override
    public void addAllVariables(Collection<CustomVariable> newVariables) {
        for (CustomVariable variable : newVariables) {
            String key = unifyKey(variable.getKey());
            remove(key);
            this.variables.put(key, variable);
        }
    }

    /**
     * removes element with given key from both maps
     * 
     * @param key the key to remove.
     */
    private void remove(String key) {
        this.strengths.remove(key);
        this.variables.remove(key);
    }

    /**
     * just ensures that the keys have a unified appearence.
     * 
     * @param key the key to unify
     * @return the unified key
     */
    private static final String unifyKey(String key) {
        return key.trim().toLowerCase();
    }

    @Override
    public Collection<Strength> getStrengths() {
        return strengths.values();
    }

    @Override
    public Collection<CustomVariable> getVariables() {
        return variables.values();
    }

    @Override
    public void clear() {
        this.strengths.clear();
        this.variables.clear();
    }

    @Override
    public Strength getStrength(String key) {
        return this.strengths.get(unifyKey(key));
    }

}
