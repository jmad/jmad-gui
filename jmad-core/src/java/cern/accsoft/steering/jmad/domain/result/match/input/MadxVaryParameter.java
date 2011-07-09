package cern.accsoft.steering.jmad.domain.result.match.input;

import cern.accsoft.steering.jmad.domain.knob.MadxParameter;

/**
 * the interface for a matchin variation parameter
 * 
 * @author muellerg
 */
public interface MadxVaryParameter {

    /**
     * returns the name of the parameter. This can be e.g. a name of a strength or the attribute of an element (e.g.
     * MCIAH->kick)
     * 
     * @return the (madx) name of the parameter to vary
     */
    public abstract String getName();

    /**
     * The approximate initial step size for varying the parameter. If the step is not entered, MAD tries to find a
     * reasonable step, but this may not always work.
     * 
     * @return the step to use.
     */
    public abstract Double getStep();

    /**
     * Lower limit for the parameter (optional)
     * 
     * @return the lower limit or null if not set
     */
    public abstract Double getLower();

    /**
     * Upper limit for the parameter (optional).
     * 
     * @return the upper limit or null if not set
     */
    public abstract Double getUpper();

    /**
     * @return the basis Parameter used for varying
     */
    public abstract MadxParameter getMadxParameter();
}
