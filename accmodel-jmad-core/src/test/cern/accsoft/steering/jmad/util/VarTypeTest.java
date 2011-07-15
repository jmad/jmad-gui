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

package cern.accsoft.steering.jmad.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VarTypeTest {

    @Test
    public void testGetVarType() {
        assertEquals(MadxVarType.DOUBLE, MadxVarType.getVarType("%le"));
        assertEquals(MadxVarType.STRING, MadxVarType.getVarType("%s"));
        assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType(""));
        assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType(null));
        assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("%S"));
        assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("%LE"));
        assertEquals(MadxVarType.STRING, MadxVarType.getVarType("%23s"));
        assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("%23S"));
        assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("a17s"));
    }

}
