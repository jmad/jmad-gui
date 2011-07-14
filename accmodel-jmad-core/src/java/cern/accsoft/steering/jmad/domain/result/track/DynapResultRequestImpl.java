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
