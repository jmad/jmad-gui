package cern.accsoft.steering.jmad.modeldefs.defs.lhc;


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
