package cern.accsoft.steering.jmad.tools.response;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

public class ResponseRequestImpl implements ResponseRequest {
    /** the correctors which to vary */
    private final List<String> correctorNames = new ArrayList<String>();

    /**
     * the values which to apply for the strengthes (+/-) (must be equal size than strengthNames)
     */
    private final List<Double> kickValues = new ArrayList<Double>();

    /** the Monitors for which to get result */
    private final List<String> monitorNames = new ArrayList<String>();

    /** Regular expressions for monitors */
    private final List<String> monitorRegexps = new ArrayList<String>();

    /** the planes for the monitors (must be of equal size than monitorNames) */
    private final List<JMadPlane> monitorPlanes = new ArrayList<JMadPlane>();

    /** the planes for the correctors (must be of equal size than monitorNames) */
    private final List<JMadPlane> correctorPlanes = new ArrayList<JMadPlane>();

    public void addCorrector(String correctorName, Double strengthValue, JMadPlane plane) {
        correctorNames.add(correctorName);
        kickValues.add(strengthValue);
        correctorPlanes.add(plane);
    }

    public void addMonitor(String monitorName, JMadPlane plane) {
        monitorNames.add(monitorName);
        monitorPlanes.add(plane);
    }

    public void clearCorrectors() {
        correctorNames.clear();
        kickValues.clear();
        correctorPlanes.clear();
    }

    public void clearMonitors() {
        monitorNames.clear();
        monitorPlanes.clear();
    }

    public void addMonitorRegexp(String regex) {
        monitorRegexps.add(regex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cern.accsoft.steering.jmad.tools.response.ResponseRequest#getCorrectorNames ()
     */
    public List<String> getCorrectorNames() {
        return correctorNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cern.accsoft.steering.jmad.tools.response.ResponseRequest#getStrengthValues ()
     */
    public List<Double> getStrengthValues() {
        return kickValues;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cern.accsoft.steering.jmad.tools.response.ResponseRequest#getMonitorNames ()
     */
    public List<String> getMonitorNames() {
        return monitorNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cern.accsoft.steering.jmad.tools.response.ResponseRequest#getMonitorPlanes ()
     */
    public List<JMadPlane> getMonitorPlanes() {
        return monitorPlanes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cern.accsoft.steering.jmad.tools.response.ResponseRequest#getCorrectorPlanes ()
     */
    public List<JMadPlane> getCorrectorPlanes() {
        return correctorPlanes;
    }

    @Override
    public List<String> getMonitorRegexps() {
        return this.monitorRegexps;
    }
}
