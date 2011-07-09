package cern.accsoft.steering.jmad.domain.result.track;

import cern.accsoft.steering.jmad.domain.track.RelativeParticleDistribution;

/**
 * Standard implementation of TrackResultRequest
 * 
 * @author xbuffat
 *
 */

public class TrackResultRequestImpl extends AbstractTrackResultRequest implements TrackResultRequest {

	private Integer printFrequency;
	
	public TrackResultRequestImpl(RelativeParticleDistribution dist) {
		super(dist);
		this.setPrintFrequency(1);
	}
	
	@Override
	public Integer getPrintFrequency() {
		return this.printFrequency;
	}

	
	@Override
	public void setPrintFrequency(Integer printfreq) {
		this.printFrequency = printfreq;
		
	}

}
