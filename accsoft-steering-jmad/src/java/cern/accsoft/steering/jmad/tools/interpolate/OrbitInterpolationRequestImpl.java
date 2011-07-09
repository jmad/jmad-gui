package cern.accsoft.steering.jmad.tools.interpolate;

import cern.accsoft.steering.jmad.domain.orbit.Orbit;

/**
 * A request for orbit interpolation. Simple implementation only taking the desired orbit object as constructor
 * argument.
 * 
 * @author muellerg
 */
public class OrbitInterpolationRequestImpl implements OrbitInterpolationRequest {

    /** the orbit to interpolate to */
    private Orbit orbit;

    public OrbitInterpolationRequestImpl(Orbit orbit) {
        this.orbit = orbit;
    }

    @Override
    public Orbit getOrbit() {
        return this.orbit;
    }

}
