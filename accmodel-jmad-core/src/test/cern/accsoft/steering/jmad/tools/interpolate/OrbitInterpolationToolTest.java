// @formatter:off
/*******************************************************************************
 * This file is part of JMad. Copyright (c) 2008-2011, CERN. All rights reserved. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.tools.interpolate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cern.accsoft.steering.jmad.JMadException;
import cern.accsoft.steering.jmad.JMadTestCase;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.JMadElementType;
import cern.accsoft.steering.jmad.domain.elem.MadxElementType;
import cern.accsoft.steering.jmad.domain.elem.impl.Corrector;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.orbit.Orbit;
import cern.accsoft.steering.jmad.domain.orbit.OrbitImpl;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;

public class OrbitInterpolationToolTest extends JMadTestCase {

    private static JMadModelDefinition modelDefinition;
    private static JMadModel model;
    private static OrbitInterpolationToolImpl interpolationTool;

    /** the twiss result of the current orbit on all elements */
    private TfsResult jmadResult = null;

    @BeforeClass
    public static void classSetUp() {
        modelDefinition = JMadTestCase.findExampleModelDefinition();
        model = getJMadService().createModel(modelDefinition);
        interpolationTool = new OrbitInterpolationToolImpl();
    }

    @Before
    public void setUp() throws Exception {
        model.init();
    }

    @Test
    public void testOpticRangeInterpolate() throws JMadException {
        /* create a new build, which is reused with each iteration */
        UpdateRequestBuilder builder = new UpdateRequestBuilder(true);
        
        for (OpticsDefinition opticsDefinition : model.getModelDefinition().getOpticsDefinitions()) {
            model.setActiveOpticsDefinition(opticsDefinition);
            for (RangeDefinition rangeDefinition : model.getModelDefinition().getRangeDefinitions()) {
                model.setActiveRangeDefinition(rangeDefinition);

                Corrector corrector = (Corrector) model.getActiveRange().getElements(JMadElementType.CORRECTOR).get(0);
                corrector.setVKick(0.000001);
                corrector.setHKick(0.000001);
                corrector.setVKick(0.000001);

                builder.setOptic(model.getOptics());

                List<Element> elements = model.getActiveRange().getElements();
                builder.setElements(model.getActiveRange().getElements());

                Map<JMadPlane, Set<Element>> monitors = extractMonitors(elements);
                for (JMadPlane plane : JMadPlane.values()) {
                    builder.setActiveMonitors(plane, monitors.get(plane));
                }

                interpolationTool.update(builder.buildRequest());

                OrbitInterpolationRequest interpolationRequest = new OrbitInterpolationRequestImpl(this
                        .createCurrentOrbit());
                OrbitInterpolationResult intRes = interpolationTool.interpolate(interpolationRequest);

                TfsResult interpolationResult = intRes.getTfsResult();
                this.compareInterpolationToOriginal(interpolationResult);
            }
        }
    }

    private Map<JMadPlane, Set<Element>> extractMonitors(List<Element> elements) {
        Map<JMadPlane, Set<Element>> monitors = new HashMap<JMadPlane, Set<Element>>();
        for (JMadPlane plane : JMadPlane.values()) {
            monitors.put(plane, new HashSet<Element>());
            for (Element element : elements) {
                if (element.getMadxElementType() == MadxElementType.MONITOR) {
                    monitors.get(plane).add(element);
                }
            }
        }

        return monitors;
    }

    private void compareInterpolationToOriginal(TfsResult interpolationResult) {
        for (String elementName : this.jmadResult.getStringData(MadxTwissVariable.NAME)) {
            if (elementName.toUpperCase().startsWith("DRIFT")) {
                continue;
            }

            Integer originalIdx = this.jmadResult.getElementIndex(elementName);
            Integer interpolatedIdx = interpolationResult.getElementIndex(elementName);

            assertNotNull("Index should be non-null for element [" + elementName + "]", interpolatedIdx);

            for (JMadPlane plane : JMadPlane.values()) {
                /* orbit */
                double originalValue = this.jmadResult.getDoubleData(JMadTwissVariable.POS.getMadxTwissVariable(plane))
                        .get(originalIdx);
                double interpolatedValue = interpolationResult.getDoubleData(
                        JMadTwissVariable.POS.getMadxTwissVariable(plane)).get(interpolatedIdx);

                assertEquals("orbit values should be equal up to 1.0e-4 for element [" + elementName + "]",
                        originalValue, interpolatedValue, 1.0e-4);

                /* angle */
                originalValue = this.jmadResult.getDoubleData(JMadTwissVariable.P.getMadxTwissVariable(plane)).get(
                        originalIdx);
                interpolatedValue = interpolationResult.getDoubleData(JMadTwissVariable.P.getMadxTwissVariable(plane))
                        .get(interpolatedIdx);

                assertEquals("angle values should be equal up to 1.0e-4 for element [" + elementName + "]",
                        originalValue, interpolatedValue, 1.0e-4);
            }
        }
    }

    private Orbit createCurrentOrbit() throws JMadModelException {
        TfsResultRequestImpl request = new TfsResultRequestImpl();
        request.addVariable(MadxTwissVariable.NAME);
        request.addVariable(MadxTwissVariable.S);
        request.addVariable(MadxTwissVariable.X);
        request.addVariable(MadxTwissVariable.Y);
        request.addVariable(MadxTwissVariable.PX);
        request.addVariable(MadxTwissVariable.PY);
        request.addVariable(MadxTwissVariable.KEYWORD);
        request.addElementFilter(".*");

        jmadResult = model.twiss(request);
        OrbitImpl orbit = new OrbitImpl();
        for (String elementName : jmadResult.getStringData(MadxTwissVariable.NAME)) {
            int index = jmadResult.getElementIndex(elementName);
            JMadElementType elementType = MadxElementType.fromMadXName(
                    jmadResult.getStringData(MadxTwissVariable.KEYWORD).get(index)).getElementType();
            if (JMadElementType.MONITOR.equals(elementType)) {
                Map<JMadPlane, Double> valueMapping = new HashMap<JMadPlane, Double>();
                for (JMadPlane plane : JMadPlane.values()) {
                    valueMapping.put(plane, jmadResult.getDoubleData(JMadTwissVariable.POS.getMadxTwissVariable(plane))
                            .get(index));
                }
                orbit.addReading(elementName, valueMapping);
            }
        }

        return orbit;
    }

    @After
    public void tearDown() throws Exception {
        model.cleanup();
    }
}
