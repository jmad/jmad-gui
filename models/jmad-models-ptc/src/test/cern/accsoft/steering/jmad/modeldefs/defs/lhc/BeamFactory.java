package cern.accsoft.steering.jmad.modeldefs.defs.lhc;


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

		Beam beam = new Beam();
		beam.setParticle(Beam.Particle.PROTON);
		beam.setEnergy(energy);
		beam.setDirection(Beam.Direction.PLUS);
		beam.setParticleNumber(1.15E11);
		beam.setRelativeEnergySpread(4.5e-4);
		return beam;
	}
}
