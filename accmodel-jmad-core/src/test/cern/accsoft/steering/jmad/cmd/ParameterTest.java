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

package cern.accsoft.steering.jmad.cmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class ParameterTest {
    Parameter param = null;

    @Test
    public void testIntegerParameter() {
        param = new GenericParameter<Integer>("testparam", 3);
        assertEquals("testparam=3", param.compose());
        assertEquals("testparam=3", param.toString());
    }

    @Test
    public void testStringParameter() {
        param = new GenericParameter<String>("testparam", "testvalue");
        assertEquals("testparam=testvalue", param.compose());
        assertEquals("testparam=testvalue", param.toString());
    }

    @Test
    public void testStringParameterQuoted() {
        param = new GenericParameter<String>("testparam", "testvalue", true);
        assertEquals("testparam=\"testvalue\"", param.compose());
        assertEquals("testparam=\"testvalue\"", param.toString());
    }

    @Test
    public void testDoubleParameter() {
        param = new GenericParameter<Double>("testparam", 0.12345);
        assertEquals("testparam=0.12345", param.compose());
    }

}
