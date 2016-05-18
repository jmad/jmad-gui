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

package cern.accsoft.steering.jmad.tools.response;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Jama.Matrix;
import cern.accsoft.steering.jmad.JMadTestCase;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public class ResponseMatrixToolTest extends JMadTestCase {

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

    @After
    public void tearDown() throws Exception {
        model.cleanup();
    }

    @Test
    public void testCalcResponseMatrix() throws JMadModelException {

        ResponseMatrixTool respTool = new FullResponseMatrixTool();

        ResponseRequestImpl request = new ResponseRequestImpl();
        request.addCorrector("MCIAV.20304", 0.5e-4, JMadPlane.V);
        request.addCorrector("MCIAV.20504", 0.5e-4, JMadPlane.V);
        request.addMonitor("BPMIH.20204", JMadPlane.H);
        request.addMonitor("BPMIH.20404", JMadPlane.H);
        request.addMonitor("BPMIV.20504", JMadPlane.V);

        Matrix responseMatrix = respTool.calcResponseMatrix(model, request);
        assertEquals("Matrix should be 3x2", 2, responseMatrix.getColumnDimension());
        assertEquals("Matrix should be 3x2", 3, responseMatrix.getRowDimension());

        // these pickups are before the kickers ;-) -> no results
        assertEquals(0.0, responseMatrix.get(0, 0), 0.00000000000001);
        assertEquals(0.0, responseMatrix.get(0, 1), 0.00000000000001);
        assertEquals(0.0, responseMatrix.get(1, 1), 0.00000000000001);
        // x-pickup, y-kick -> no result
        assertEquals(0.0, responseMatrix.get(1, 0), 0.00000000000001);

        // these pickups should return values:
        // strength ACIBV.20304, monitor BPMIV.20504,
        // TODO: rechecked manually with madx!
        assertEquals(90.86729324, responseMatrix.get(2, 0), 0.00000001);

        // BPMIV.20504 again seems to be before RCIBV.20504 -> no result.
        assertEquals(0.0, responseMatrix.get(2, 1), 0.00000000001);
    }

}
