package cern.accsoft.steering.jmad.tools.interpolate;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;

/**
 * This interface represents the result of an orbit interpolation.
 * 
 * @author muellerg
 */
public interface OrbitInterpolationResult {

    /**
     * @return the resulting interpolated orbit as a {@link TfsResult}.
     */
    public abstract TfsResult getTfsResult();
}
