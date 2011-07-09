package cern.accsoft.steering.jmad.domain.result.tfs;

import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * Defines which madx-variables shall be contained in the {@link TfsResult} after the twiss.
 * 
 * @author kaifox
 */
public interface TfsResultRequest {

    /**
     * @return the requeset resultVariables.
     */
    public abstract List<TwissVariable> getResultVariables();

    /**
     * @return the requested element-filters.
     */
    public abstract List<String> getElementPattern();

    /**
     * @return the requested element-classes
     */
    public abstract List<String> getElementClasses();

    /**
     * @return <code>true</code>, if it is required to run a ptc-twiss to get the variables
     */
    public abstract boolean needsPtcTwiss();
}