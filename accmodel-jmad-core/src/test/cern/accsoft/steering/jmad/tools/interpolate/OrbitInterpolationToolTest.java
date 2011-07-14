// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
        for (OpticsDefinition opticsDefinition : model.getModelDefinition().getOpticsDefinitions()) {
            model.setActiveOpticsDefinition(opticsDefinition);
            for (RangeDefinition rangeDefinition : model.getModelDefinition().getRangeDefinitions()) {
                model.setActiveRangeDefinition(rangeDefinition);

                Corrector corrector = (Corrector) model.getActiveRange().getElements(JMadElementType.CORRECTOR).get(0);
                corrector.setVKick(0.000001);
                corrector.setHKick(0.000001);
                corrector.setVKick(0.000001);

                UpdateRequest request = new UpdateRequestBuilder(true)//
                        .fullUpdate(model.getActiveRange().getElements(),
                                this.extractMonitors(model.getActiveRange().getElements()), model.getOptics())//
                        .buildRequest();
                interpolationTool.update(request);

                OrbitInterpolationRequest interpolationRequest = new OrbitInterpolationRequestImpl(
                        this.createCurrentOrbit());
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
                double originalValue = this.jmadResult.getDoubleData(JMadTwissVariable.POS.getMadxTwissVariable(plane))
                        .get(originalIdx);
                double interpolatedValue = interpolationResult.getDoubleData(
                        JMadTwissVariable.POS.getMadxTwissVariable(plane)).get(interpolatedIdx);

                assertEquals("Values should be equal up to 1.0e-4 for element [" + elementName + "]", originalValue,
                        interpolatedValue, 1.0e-4);
            }
        }
    }

    private Orbit createCurrentOrbit() throws JMadModelException {
        TfsResultRequestImpl request = new TfsResultRequestImpl();
        request.addVariable(MadxTwissVariable.NAME);
        request.addVariable(MadxTwissVariable.S);
        request.addVariable(MadxTwissVariable.X);
        request.addVariable(MadxTwissVariable.Y);
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
