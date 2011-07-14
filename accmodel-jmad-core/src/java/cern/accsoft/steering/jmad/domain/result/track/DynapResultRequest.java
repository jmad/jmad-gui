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
 * Defines the information wanted in the DynapResult
 * 
 * @author xbuffat
 *
 */

public interface DynapResultRequest {
	
	/**
	 * 
	 * @param turns Number of turns to track
	 */
	void setTurns(Integer turns);
	
	/**
	 * 
	 * @return Number of turns to track
	 */
	Integer getTurns();
	
	/**
	 * 
	 * @param fastTune If true, the only tunes of the particle tracked is returned 
	 */
	void setFastTune(boolean fastTune);
	/**
	 * 
	 * @return If true, the only tunes of the particle tracked is returned
	 */
	boolean isFastTune();
	
	/**
	 * 
	 * @param Lyapunov The launch distance between two companion particles added to the x coordinate
	 */
	void setLyapunov(double Lyapunov);
	
	/**
	 * 
	 * @return The launch distance between two companion particles added to the x coordinate
	 */
	double getLyapunov();
	
	/**
	 * 
	 * @param orbit If set, the flag orbit is true during the tracking and its initialization
	 */
	void setOrbit(boolean orbit);
	
	/**
	 * 
	 * @return If set, the flag orbit is true during the tracking and its initialization
	 */
	boolean isOrbit();
	
	/**
	 * 
	 * @param apertureLimited if true, the particle reaching aperture limit are considered lost.
	 */
	void setApertureLimited(boolean apertureLimited);
	
	/**
	 * 
	 * @return if true, the particle reaching aperture limit are considered lost.
	 */
	boolean isApertureLimited();

	/**
	 * Particles reaching these values are considered lost if this.isApertureLimited()
	 * 
	 * @param x
	 * @param px
	 * @param y
	 * @param py
	 * @param t
	 * @param pt
	 */
	void setApertureLimitation(double x,double px,double y,double py,double t,double pt);
	/**
	 * 
	 * @return Particles reaching these values of x,px,y,py,t,pt are considered lost if this.isApertureLimited()
	 */
	Double[] getApertureLimitation();
	
	/**
	 * 
	 * @param relatvieParticleDistribution Initial coordinates of the particles to be tracked
	 */
	void setRelativeParticleDisctribution(RelativeParticleDistribution relatvieParticleDistribution);
	
	/**
	 * 
	 * @return Initial coordinates of the particles to be tracked
	 */
	RelativeParticleDistribution getRelativeParticleDistribution();

}
