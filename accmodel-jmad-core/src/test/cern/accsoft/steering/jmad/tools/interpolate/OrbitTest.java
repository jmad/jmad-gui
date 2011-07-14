// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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
