package cern.accsoft.steering.jmad.domain.orbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

public class OrbitImpl implements Orbit {
    /** this list of the monitor names where there are measurements available in the orbit */
    private List<String> monitorNames;

    /** the orbit values mapped by plane */
    private Map<JMadPlane, List<Double>> values;

    /** the indices of the monitors in the value lists */
    private Map<String, Integer> monitorIndices;

    /** the default constructor */
    public OrbitImpl() {
        this.monitorNames = new ArrayList<String>();
        this.values = new HashMap<JMadPlane, List<Double>>();
        for (JMadPlane plane : JMadPlane.values()) {
            this.values.put(plane, new ArrayList<Double>());
        }
        this.monitorIndices = new HashMap<String, Integer>();
    }

    /**
     * Add a monitor reading to the orbit object. The monitor name will be stored in upper case in the orbit object.
     * 
     * @param monitorName the name of the monitor
     * @param values the [plane, value] mapping for the monitor reading
     */
    public void addReading(String monitorName, Map<JMadPlane, Double> values) {
        this.monitorNames.add(monitorName.toUpperCase());
        this.monitorIndices.put(monitorName, this.monitorNames.size() - 1);
        for (JMadPlane plane : JMadPlane.values()) {
            this.values.get(plane).add(values.get(plane));
        }
    }

    @Override
    public List<String> getMonitorNames() {
        return this.monitorNames;
    }

    @Override
    public List<Double> getValues(JMadPlane plane) {
        return this.values.get(plane);
    }

    @Override
    public Integer getMonitorIndex(String monitorName) {
        return this.monitorIndices.get(monitorName);
    }

}
