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
