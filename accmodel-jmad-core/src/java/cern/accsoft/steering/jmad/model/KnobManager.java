/**
 * 
 */
package cern.accsoft.steering.jmad.model;

import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.KnobType;

/**
 * This is the interface of a class, which manages several knobs
 * 
 * @author kfuchsbe
 */
public interface KnobManager {

    /**
     * @return all available custom knobs
     */
    public List<Knob> getCustomKnobs();

    /**
     * @param knob the knob to add
     */
    public void addCustomKnob(Knob knob);

    /**
     * removes all the knobs, except the custom ones.
     */
    public void cleanup();

    /**
     * retrieves a knob given by the type and the key within the type
     * 
     * @param type the type of the knob
     * @param key the unique key within the type
     * @return the knob
     */
    public Knob getKnob(KnobType type, String key);

}
