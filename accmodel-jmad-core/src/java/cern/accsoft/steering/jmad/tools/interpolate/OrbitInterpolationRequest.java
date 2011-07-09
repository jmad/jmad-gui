package cern.accsoft.steering.jmad.tools.interpolate;

import cern.accsoft.steering.jmad.domain.orbit.Orbit;


/**
 * This interface defines a request for an orbit interpolation.
 * 
 * @author muellerg
 */
public interface OrbitInterpolationRequest {

    /**
     * @return the orbit to interpolate
     */
    public abstract Orbit getOrbit();
}
