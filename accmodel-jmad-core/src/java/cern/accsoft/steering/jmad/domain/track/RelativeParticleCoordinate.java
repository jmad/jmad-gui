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
package cern.accsoft.steering.jmad.domain.track;

/**
 * 
 * describes the position in 6D phase space of a particle relatively to a reference one.
 * 
 * @author xbuffat
 *
 */

public interface RelativeParticleCoordinate {

	/**
	 * 
	 * @return X
	 */
	double getXRelatviePosition();
	/**
	 * 
	 * @return PX = px/p0
	 */
	double getXRelativeMomentum();
	/**
	 * 
	 * @return Y
	 */
	double getYRelativePosition();
	/**
	 * 
	 * @return PY = py/p0
	 */
	double getYRelatvieMomentum();
	
	/**
	 * 
	 * @return T = -c*t
	 */
	double getRelativeTimeDifference();
	
	/**
	 * 
	 * @return PT = delta(E)/ps*c
	 */
	double getRelativeEnergyError();
	

	void setXRelatviePosition(double x);
	void setXRelativeMomentum(double px);
	void setYRelativePosition(double y);
	void setYRelatvieMomentum(double py);
	void setRelativeTimeDiffence(double t);
	void setRelativeEnergyError(double pt);
}
