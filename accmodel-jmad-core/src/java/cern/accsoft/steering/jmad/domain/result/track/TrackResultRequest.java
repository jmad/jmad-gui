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
 * 
 * Defines the information wanted in the TrackResult
 * 
 * @author xbuffat
 *
 */

public interface TrackResultRequest {

	/**
	 * 
	 * @param turns number of turn to track
	 */
	void setTurns(Integer turns);
	/**
	 * 
	 * @return number of turns to track
	 */
	Integer getTurns();

	/**
	 * 
	 * @param printfreq The properties of the particles tracked are stored every printfreq turns
	 */
	void setPrintFrequency(Integer printfreq);
	/**
	 * 
	 * @return The properties of the particles tracked are stored every printfrequency turns
	 */
	Integer getPrintFrequency();

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
