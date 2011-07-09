package cern.accsoft.steering.jmad.domain.result.track;

import cern.accsoft.steering.jmad.domain.track.RelativeParticleDistribution;

/**
 * 
 * This class provides methods common to ResultRequests involving tracking. 
 * 
 * @author xbuffat
 *
 */

public class AbstractTrackResultRequest {
	
	private boolean apertureLimited;
	private Double[] apertureLimitation;
	private Integer turns;
	private RelativeParticleDistribution relativeParticleDistribution;
	
	public AbstractTrackResultRequest(RelativeParticleDistribution dist) {
		this.relativeParticleDistribution = dist;
		this.setApertureLimited(false);
		this.setApertureLimitation(0, 0, 0, 0, 0, 0);
		this.setTurns(1);
	}
	
	public void setApertureLimitation(double x, double px, double y, double py,
			double t, double pt) {
		this.apertureLimitation = new Double[6];
		this.apertureLimitation[0] = x;
		this.apertureLimitation[1] = px;
		this.apertureLimitation[2] = y;
		this.apertureLimitation[3] = py;
		this.apertureLimitation[4] = t;
		this.apertureLimitation[5] = pt;
		
	}

	public void setTurns(Integer turns) {
		this.turns = turns;
		
	}

	public Double[] getApertureLimitation() {
		return this.apertureLimitation;
	}
	
	public Integer getTurns() {
		return this.turns;
	}

	public boolean isApertureLimited() {
		return this.apertureLimited;
	}

	public void setApertureLimited(boolean apertureLimited) {
		this.apertureLimited = apertureLimited;
		
	}

	public RelativeParticleDistribution getRelativeParticleDistribution() {
		return this.relativeParticleDistribution;
	}

	public void setRelativeParticleDisctribution(
			RelativeParticleDistribution relativeParticleDistribution) {
		this.relativeParticleDistribution = relativeParticleDistribution;
		
	}

}
