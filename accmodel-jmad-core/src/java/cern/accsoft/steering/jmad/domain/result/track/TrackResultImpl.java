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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTrackVariable;

/**
 * Standard implementation of TrackResult
 * 
 * @author xbuffat
 *
 */

public class TrackResultImpl implements TrackResult {

	private Map<MadxTrackVariable,List<List<Double>>> data;
	
	public TrackResultImpl() {
		this.data = new HashMap<MadxTrackVariable,List<List<Double>>>();
		for(MadxTrackVariable variable : MadxTrackVariable.values())
		{
			this.data.put(variable, new ArrayList<List<Double>>());
		}
	}
	
	@Override
    public ResultType getResultType() {
		return ResultType.TRACK_RESULT;
	}

	@Override
	public Integer getParticleCount() {
		return this.data.get(MadxTrackVariable.X).size();
	}

	@Override
	public void add(MadxTrackVariable variable, Integer particleNumber, Double value) {
		int particleIndex = particleNumber-1;
		while(this.data.get(variable).size()<=particleIndex)
		{
			this.data.get(variable).add(new ArrayList<Double>());
		}
		
		this.data.get(variable).get(particleIndex).add(value);
	}

	@Override
	public List<Double> get(MadxTrackVariable variable, Integer particleIndex) {
		if(particleIndex<this.data.get(variable).size())
		{
			return this.data.get(variable).get(particleIndex);
		}
		else
		{
			return new ArrayList<Double>();
		}
	}
	
	@Override
	public void clear() {
		for(MadxTrackVariable variable : MadxTrackVariable.values())
		{
			this.data.get(variable).clear();
		}
	}
}
