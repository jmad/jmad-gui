// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.tools.interpolate;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Jama.Matrix;
import cern.accsoft.steering.jmad.JMadTestCase;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public class TransferMatrixCalculatorTest extends JMadTestCase {

    private static JMadModelDefinition modelDefinition;
    private static JMadModel model;

    @BeforeClass
    public static void classSetUp() {
        modelDefinition = JMadTestCase.findExampleModelDefinition();
        model = getJMadService().createModel(modelDefinition);
    }

    @Before
    public void setUp() throws Exception {
        model.init();
    }

    @Test
    public void testCalculateTransferMatrices() throws JMadModelException {
        Optic optic = model.getOptics();
        for (Entry<List<String>, Map<JMadPlane, Matrix>> elementEntry : this.createTransferMatrixMapping().entrySet()) {
            OpticPoint from = optic.getPointByName(elementEntry.getKey().get(0));
            assertNotNull("Optic point for from element should exist", from);

            OpticPoint to = optic.getPointByName(elementEntry.getKey().get(1));
            assertNotNull("Optic point for to element should exist", to);

            for (Entry<JMadPlane, Matrix> planeEntry : elementEntry.getValue().entrySet()) {
                Matrix tfM = TransferMatrixCalculator.calculate(planeEntry.getKey(), from, to);
                Matrix refM = planeEntry.getValue();
                assertEquals("M11 should be equal for " + from.getName() + " -> " + to.getName(), refM.get(0, 0),
                        tfM.get(0, 0), 1.0e-12);
                assertEquals("M12 should be equal for " + from.getName() + " -> " + to.getName(), refM.get(0, 1),
                        tfM.get(0, 1), 1.0e-12);
                assertEquals("M21 should be equal for " + from.getName() + " -> " + to.getName(), refM.get(1, 0),
                        tfM.get(1, 0), 1.0e-12);
                assertEquals("M22 should be equal for " + from.getName() + " -> " + to.getName(), refM.get(1, 1),
                        tfM.get(1, 1), 1.0e-12);
            }
        }
    }

    @Test
    public void testMatrix() {
        double[][] values = new double[][] { new double[] { 11.0, 12.0 }, new double[] { 21.0, 22.0 } };
        Matrix A = new Matrix(values);

        assertEquals("M11 should be right", 11.0, A.get(0, 0), 0.0);
        assertEquals("M12 should be right", 12.0, A.get(0, 1), 0.0);
        assertEquals("M21 should be right", 21.0, A.get(1, 0), 0.0);
        assertEquals("M22 should be right", 22.0, A.get(1, 1), 0.0);

        values = new double[][] { new double[] { 11.0, 12.0 }, new double[] { 21.0, 22.0 } };
        Matrix B = new Matrix(values);

        values = new double[][] { new double[] { 22.0, 24.0 }, new double[] { 42.0, 44.0 } };
        Matrix C = new Matrix(values);

        Matrix D = A.plus(B);

        assertEquals("M11 should be equal after add", C.get(0, 0), D.get(0, 0), 0.0);
        assertEquals("M12 should be equal after add", C.get(0, 1), D.get(0, 1), 0.0);
        assertEquals("M21 should be equal after add", C.get(1, 0), D.get(1, 0), 0.0);
        assertEquals("M22 should be equal after add", C.get(1, 1), D.get(1, 1), 0.0);
    }

    private Map<List<String>, Map<JMadPlane, Matrix>> createTransferMatrixMapping() {
        Map<List<String>, Map<JMadPlane, Matrix>> mapping = new HashMap<List<String>, Map<JMadPlane, Matrix>>();

        ArrayList<String> names;
        Map<JMadPlane, Matrix> planesMapping;
        Matrix hMatrix;
        Matrix vMatrix;
        double[][] values;

        names = new ArrayList<String>();
        names.add("MDAV.610013");
        names.add("BPCK.610211");

        planesMapping = new HashMap<JMadPlane, Matrix>();

        values = new double[][] { new double[] { 1.765828821230122, 39.528798403662634 },
                new double[] { -0.060120512625108, -0.779515889045146 } };
        hMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.H, hMatrix);

        values = new double[][] { new double[] { -0.596370376862546, 54.120599366091703 },
                new double[] { -0.044459853112010, 2.357920434526354 } };
        vMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.V, vMatrix);

        mapping.put(names, planesMapping);

        names = new ArrayList<String>();
        names.add("BPCK.610312");
        names.add("MBB.610413");

        planesMapping = new HashMap<JMadPlane, Matrix>();

        values = new double[][] { new double[] { 0.420324768768305, 26.020209460934854 },
                new double[] { -0.036669814131149, 0.109067461211684 } };
        hMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.H, hMatrix);

        values = new double[][] { new double[] { 1.600440044003578, 54.619268133519448 },
                new double[] { 0.038031407710023, 1.922750975107488 } };
        vMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.V, vMatrix);

        mapping.put(names, planesMapping);

        names = new ArrayList<String>();
        names.add("QTLF.610600");
        names.add("BPMIV.20504");

        planesMapping = new HashMap<JMadPlane, Matrix>();

        values = new double[][] { new double[] { 1.1285279724185, 100.8577817821102 },
                new double[] { 0.0072709736625, 1.5359249547752 } };
        hMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.H, hMatrix);

        values = new double[][] { new double[] { -2.786948878676446, 21.562176772051437 },
                new double[] { 0.065813921761709, -0.868007100380250 } };
        vMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.V, vMatrix);

        mapping.put(names, planesMapping);

        names = new ArrayList<String>();
        names.add("BPMIV.20704");
        names.add("MCIAH.20804");

        planesMapping = new HashMap<JMadPlane, Matrix>();

        values = new double[][] { new double[] { 0.917825840515233, 28.543153977863987 },
                new double[] { -0.046984968428656, -0.371638249270382 } };
        hMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.H, hMatrix);

        values = new double[][] { new double[] { 1.083292030296537, 33.351230860441376 },
                new double[] { 0.048030845478715, 2.401834171410443 } };
        vMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.V, vMatrix);

        mapping.put(names, planesMapping);

        names = new ArrayList<String>();
        names.add("BPMIH.21004");
        names.add("MCIAH.21604");

        planesMapping = new HashMap<JMadPlane, Matrix>();

        values = new double[][] { new double[] { -2.319494429875103, -97.588721950970140 },
                new double[] { 0.067211777888265, 2.396691034290412 } };
        hMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.H, hMatrix);

        values = new double[][] { new double[] { 0.518028815907448, -19.009277237013574 },
                new double[] { 0.066776190230579, -0.519984805194233 } };
        vMatrix = new Matrix(values);
        planesMapping.put(JMadPlane.V, vMatrix);

        mapping.put(names, planesMapping);

        return mapping;
    }

    @After
    public void tearDown() throws Exception {
        model.cleanup();
    }
}
