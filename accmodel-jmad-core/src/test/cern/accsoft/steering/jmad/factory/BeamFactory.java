// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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

import cern.accsoft.steering.jmad.MadXConstants;
import cern.accsoft.steering.jmad.domain.beam.Beam;

public final class BeamFactory {

	private BeamFactory() {
		// only static methods
	}
	
	/**
	 * creates the beam which can be used by default for LHC sequences
	 * 
	 * @return the beam
	 */
	public static Beam createDefaultLhcBeam() {
		Double energy = 450.0; // energy in GeV
		Double gamma = energy / MadXConstants.MASS_PROTON; // beta
		Double emittance = 3.5e-06; // normalized emittance
		Double xEmittance = emittance / (gamma);
		Double yEmittance = emittance / (gamma);

		Beam beam = new Beam();
		beam.setParticle(Beam.Particle.PROTON);
		beam.setEnergy(energy);
		beam.setBunchLength(0.077);
		beam.setDirection(Beam.Direction.PLUS);
		beam.setParticleNumber(1.1E11);
		beam.setRelativeEnergySpread(5e-4);
		beam.setHorizontalEmittance(xEmittance);
		beam.setVerticalEmittance(yEmittance);
		return beam;
	}
}
