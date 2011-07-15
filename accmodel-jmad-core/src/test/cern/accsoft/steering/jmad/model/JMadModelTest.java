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

package cern.accsoft.steering.jmad.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cern.accsoft.steering.jmad.JMadTestCase;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequest;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public class JMadModelTest extends JMadTestCase {

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
    public void testGetSummary() throws JMadModelException {
        TfsResultRequest request = TfsResultRequestImpl.createSummaryOnlyRequest();
        TfsResult result = model.twiss(request);
        assertNotNull(result);
        /*
         * we get one column, but there should be nothing inside!
         */
        assertEquals(1, result.getColumnCount());
        assertEquals(1, result.getKeys().size());
        String key = result.getKeys().get(0);
        assertEquals(0, result.getDoubleData(key).size());
    }

}
