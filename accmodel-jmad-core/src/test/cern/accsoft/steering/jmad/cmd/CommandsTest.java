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
package cern.accsoft.steering.jmad.cmd;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.elem.MadxElementType;
import cern.accsoft.steering.jmad.domain.elem.impl.Quadrupole;
import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.kernel.cmd.AssignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.BeamCommand;
import cern.accsoft.steering.jmad.kernel.cmd.CallCommand;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.DefineElement;
import cern.accsoft.steering.jmad.kernel.cmd.TwissCommand;
import cern.accsoft.steering.jmad.kernel.cmd.UseCommand;
import cern.accsoft.steering.jmad.kernel.cmd.ValueCommand;
import cern.accsoft.steering.jmad.kernel.cmd.WriteCommand;

public class CommandsTest {
	private Command command = null;
	private File file = new File("testfile.txt");

	@Test
	public void testCallCommand() {
		command = new CallCommand(file);
		assertEquals("call, file=\"" + file.getAbsolutePath() + "\";", command
				.compose());
	}

	@Test
	public void testUseCommand() {
		command = new UseCommand("testseq");
		assertEquals("use, period=testseq;", command.compose());

		command = new UseCommand("testseq", new MadxRange("MAB1", "MAB5"));
		assertEquals("use, period=testseq, range=MAB1/MAB5;", command.compose());
	}

	@Test
	public void testBeamCommand() {

		// 
		Beam beam = new Beam();
		beam.setParticle(Beam.Particle.PROTON);
		beam.setSequence("ti2");
		beam.setEnergy(450.0);
		beam.setBunchLength(0.077);
		beam.setDirection(Beam.Direction.PLUS);
		beam.setParticleNumber(1.1E11);
		beam.setRelativeEnergySpread(5e-4);
		beam.setHorizontalEmittance(1.6e-8);
		beam.setVerticalEmittance(1.4e-8);

		command = new BeamCommand(beam);
		assertEquals(
				"beam, particle=proton, energy=450.0, ex=1.6E-8, ey=1.4E-8, "
						+ "sigt=0.077, sige=5.0E-4, npart=1.1E11, bv=1, sequence=ti2;",
				command.compose());

		// just set some other values to check functionality

		beam = new Beam();

		beam.setParticleName("myparticle");
		beam.setMass(0.5E-3);
		beam.setCharge(1.0);
		beam.setMomentum(20.5);
		beam.setGamma(1.23);
		beam.setLongitudinalEmittance(2.34);
		beam.setNormalisedHorizontalEmittance(3.45);
		beam.setNormalisedVerticalEmittance(4.56);
		beam.setBunchNumber(2808);
		beam.setBunchCurrent(1.5);
		beam.setBunched(true);
		beam.setRadiate(false);

		command = new BeamCommand(beam);
		assertEquals(
				"beam, particle=myparticle, mass=5.0E-4, charge=1.0, pc=20.5, "
						+ "gamma=1.23, et=2.34, exn=3.45, eyn=4.56, kbunch=2808, bcurrent=1.5, "
						+ "bunched;", command.compose());
	}

	@Test
	public void testTwissCommand() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"test-twiss");

		twiss.setDeltap(0.0);
		twiss.setBetx(1.0);
		twiss.setAlfx(0.2);
		twiss.setDx(3.0);
		twiss.setDpx(0.4);
		twiss.setBety(5.0);
		twiss.setAlfy(0.6);
		twiss.setDy(7.0);
		twiss.setDpy(0.8);

		command = new TwissCommand(twiss);

		assertEquals(
				"twiss, deltap=0.0, betx=1.0, bety=5.0, alfx=0.2, alfy=0.6, "
						+ "dx=3.0, dy=7.0, dpx=0.4, dpy=0.8, chrom;", command
						.compose());
	}

	@Test
	public void testAssignCommand() {
		command = new AssignCommand(file);
		assertEquals("assign, echo=\"" + file.getAbsolutePath() + "\";",
				command.compose());

		command = new AssignCommand();
		assertEquals("assign, echo=terminal;", command.compose());
	}

	@Test
	public void testValueCommand() {
		ArrayList<String> valueNames = new ArrayList<String>();
		valueNames.add("abcd");
		command = new ValueCommand(valueNames);
		assertEquals("value, abcd;", command.compose());

		valueNames.clear();
		valueNames.add("abcd");
		valueNames.add("def");
		valueNames.add("ghi");
		command = new ValueCommand(valueNames);
		assertEquals("value, abcd, def, ghi;", command.compose());
	}
	
	@Test
	public void testWriteCommand() {
		WriteCommand command = new WriteCommand("dynap","fileName");
		assertEquals("write, table=\"dynap\", file=\"fileName\";",command.compose());
		
		WriteCommand command2 = new WriteCommand("dynap");
		assertEquals("write, table=\"dynap\";",command2.compose());
	}
	
	@Test
	public void testDefineElement() {
		Quadrupole element = new Quadrupole(MadxElementType.QUADRUPOLE, "testQuad");
		element.setPosition(3.14145);
		element.setAttribute("k1", 1.0);
		element.setAttributesInitialized(true);
		DefineElement command = new DefineElement(element);
		System.out.println(command.compose());
		assertEquals(command.compose(),
				"testQuad: quadrupole, k1=1.0;");
	}
}
