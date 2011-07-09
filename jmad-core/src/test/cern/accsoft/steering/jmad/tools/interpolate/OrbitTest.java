package cern.accsoft.steering.jmad.tools.interpolate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

import cern.accsoft.steering.jmad.domain.orbit.OrbitImpl;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

public class OrbitTest {

    @Test
    public void testOrbitCreation() {
        Map<String, Map<JMadPlane, Double>> values = new HashMap<String, Map<JMadPlane, Double>>();
        String[] elementNames = new String[] { "element1", "otto_king_karl", "karl_der_kurze" };
        Random random = new Random();
        OrbitImpl orbit = new OrbitImpl();
        for (String elementName : elementNames) {
            Map<JMadPlane, Double> elemValues = new HashMap<JMadPlane, Double>();
            for (JMadPlane plane : JMadPlane.values()) {
                elemValues.put(plane, random.nextDouble());
            }
            orbit.addReading(elementName, elemValues);
            values.put(elementName, elemValues);
        }

        for (String elementName : elementNames) {
            int index = orbit.getMonitorIndex(elementName);
            for (JMadPlane plane : JMadPlane.values()) {
                double value = orbit.getValues(plane).get(index);
                Assert.assertEquals("Value should be identical for element [" + elementName, values.get(elementName)
                        .get(plane).doubleValue(), value, 0.0);
            }
        }
    }
}
