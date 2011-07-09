/**
 * 
 */
package cern.accsoft.steering.jmad.model;

/**
 * Defines in which mode the model is working.
 * 
 * @author kaifox
 */
public enum ModelMode {
    /** The JMad model is working in normal madx mode. */
    MADX,

    /** The JMad model works in ptc mode. That means the ptc-universe was started and is also done ptc-like. */
    PTC;
}
