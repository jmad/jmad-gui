/**
 * 
 */
package cern.accsoft.steering.jmad.domain.knob;

/**
 * This enum represents the type of a knob
 * 
 * @author kfuchsbe
 */
public enum KnobType {
    STRENGTH("str"), ELEMENT_ATTRIBUTE("atb"), TWISS_INITIAL_CONDITION("tic"), CUSTOM("cst"), MISALIGNMENT("mal");

    private String prefix;

    private KnobType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

}
