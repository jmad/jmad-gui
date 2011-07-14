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
				+ "sigt=0.077, sige=5.0E-4, npart=1.1E11, bv=1;", 
				command.compose());
		
		
		

	}

}
