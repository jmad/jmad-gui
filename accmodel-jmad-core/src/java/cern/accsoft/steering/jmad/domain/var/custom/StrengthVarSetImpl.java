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
package cern.accsoft.steering.jmad.domain.var.custom;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.knob.strength.Strength;

/**
 * The default implementation of a {@link StrengthVarSet}
 * 
 * @author kfuchsbe
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
