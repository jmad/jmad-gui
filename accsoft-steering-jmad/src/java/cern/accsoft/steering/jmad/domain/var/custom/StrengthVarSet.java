/**
 * 
 */
package cern.accsoft.steering.jmad.domain.var.custom;

import java.util.Collection;

import cern.accsoft.steering.jmad.domain.knob.strength.Strength;

/**
 * A set of strengthes and variables, like it is created when parsing the strength files. It allows to put new
 * strengthes and variables. Nevertheless if variables or strengthes with the same name already exist, then they
 * override the old ones! This is in order to correctly deal with optics definitions and optics overlays.
 * 
 * @author kfuchsbe
 */
public interface StrengthVarSet {

    /**
     * removes all the strengths and vars from the set.
     */
    public void clear();

    /**
     * @return all the available variables (e.g. read from a strength-file)
     */
    public Collection<CustomVariable> getVariables();

    /**
     * @return all the available strengths (e.g. read from a strength-file)
     */
    public Collection<Strength> getStrengths();

    /**
     * adds all the variables and replaces variables and strengths of the same name.
     * 
     * @param variables the variables to add
     */
    public void addAllVariables(Collection<CustomVariable> variables);

    /**
     * adds all the strengths and replaces variables and strengths of the same name.
     * 
     * @param strengths the strengths to add
     */
    public void addAllStrengths(Collection<Strength> strengths);

    /**
     * find the strength of the given key
     * 
     * @param key the key of the strength
     * @return the strength or <code>null</code> if no strength was found for the give key.
     */
    public Strength getStrength(String key);
}
