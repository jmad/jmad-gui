/**
 * 
 */
package cern.accsoft.steering.jmad.domain.knob;

/**
 * This interface characterizes a knob to the model. This has nothing to do with knobs in lsa etc! This Knob in
 * principle is just a method to tune multiple values of the model in one go
 * 
 * @author kfuchsbe
 */
public interface Knob {

    /**
     * @return the name of the knob
     */
    public String getName();

    /**
     * @return the actual value
     */
    public double getValue();

    /**
     * set the actual value
     * 
     * @param value the value to set
     */
    public void setValue(double value);

    /**
     * set the actual offset of the knob
     * 
     * @param value the offset to set
     */
    public void setOffset(double value);

    /**
     * @return the actual offset of the knob
     */
    public double getOffset();

    /**
     * @return a description for this knob
     */
    public String getDescription();

    /**
     * @return the total value (value+offset)
     */
    public double getTotalValue();

    /**
     * @return the type of this knob
     */
    public KnobType getType();

    /**
     * @return a unique key (within all knobs of the same type)
     */
    public String getKey();

    /**
     * adds a listener which gets notified, if the knob value changes
     * 
     * @param listener the listener to add
     */
    public void addListener(KnobListener listener);

    /**
     * removes a listener if available.
     * 
     * @param listener the listener to remove
     */
    public void removeListener(KnobListener listener);
}
