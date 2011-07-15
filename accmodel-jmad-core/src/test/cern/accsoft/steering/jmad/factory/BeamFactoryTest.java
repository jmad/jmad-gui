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

package cern.accsoft.steering.jmad.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cern.accsoft.steering.jmad.kernel.cmd.BeamCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;

public class BeamFactoryTest {

    @Test
    public void testCreateLhcBeam() {
        Command command = new BeamCommand(BeamFactory.createDefaultLhcBeam());

        assertEquals("beam, particle=proton, energy=450.0, ex=7.297671095555556E-9, ey=7.297671095555556E-9, "
                + "sigt=0.077, sige=5.0E-4, npart=1.1E11, bv=1;", command.compose());

    }

}
