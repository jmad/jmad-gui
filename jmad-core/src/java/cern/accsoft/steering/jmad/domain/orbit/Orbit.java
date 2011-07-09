package cern.accsoft.steering.jmad.domain.orbit;

import java.util.List;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * This interface represents an orbit for one plane.
 * 
 * @author muellerg
 */
public interface Orbit {

    /**
     * @return the list of monitor names defined for this orbit. the monitor names are in upper case in the orbit
     *         object.
     */
    public abstract List<String> getMonitorNames();

    /**
     * Retrieve the measured values for the given plane.
     * 
     * @param plane the {@link JMadPlane} to get the values for
     * @return the list of values
     */
    public abstract List<Double> getValues(JMadPlane plane);

    /**
     * Retrieve the index in the data list for the given monitor. The monitor name will be cast to upper case.
     * 
     * @param monitorName the name of the monitor to get the data index for
     * @return the index in the value list
     */
    public abstract Integer getMonitorIndex(String monitorName);
}
