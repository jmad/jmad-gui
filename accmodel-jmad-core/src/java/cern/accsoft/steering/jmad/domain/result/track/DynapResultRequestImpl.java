package cern.accsoft.steering.jmad.domain.result.track;

import cern.accsoft.steering.jmad.domain.track.RelativeParticleDistribution;

/**
 * Standard implementation of DynapResultRequest
 * 
 * @author xbuffat
 *
 */

public class DynapResultRequestImpl extends AbstractTrackResultRequest implements DynapResultRequest {

	private boolean fastTune;
	private double lyapunov;
	private boolean orbit;
	
	public DynapResultRequestImpl(RelativeParticleDistribution dist) {
		super(dist);
		this.setFastTune(false);
		this.setLyapunov(1.0E-7);
		this.setOrbit(true);
	}

	@Override
	public boolean isFastTune() {
		return this.fastTune;
	}

	@Override
	public double getLyapunov() {
		return this.lyapunov;
	}

	@Override
	public boolean isOrbit() {
		return this.orbit;
	}

	@Override
	public void setFastTune(boolean fastTune) {
		this.fastTune = fastTune;
		
	}

	@Override
	public void setLyapunov(double lyapunov) {
		this.lyapunov = lyapunov;
		
	}

	@Override
	public void setOrbit(boolean orbit) {
		this.orbit = orbit;
		
	}

}
