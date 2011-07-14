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
