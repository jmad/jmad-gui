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

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTrackVariable;

/**
 * 
 * This interface allow to manage data contained in the output of a track task.
 * 
 * @author xbuffat
 *
 */

public interface TrackResult extends Result {

	/**
	 * 
	 * @return number of particle tracked
	 */
	Integer getParticleCount();
	
	/**
	 * 
	 * Store the value associated to a variable for a particle
	 * 
	 * @param variable
	 * @param particle integer associated to the tracked particle
	 * @param value
	 */
	void add(MadxTrackVariable variable,Integer particle, Double value);
	
	/**
	 * 
	 * @param variable
	 * @param particle integer associated to the tracked particle from 0 to getParticleCount()
	 * @return values associated to a variable for a particle
	 */
	List<Double> get(MadxTrackVariable variable, Integer particle);
	
	/**
	 * clear the result
	 */
	void clear();
}
