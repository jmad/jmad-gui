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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.var.enums.MadxDynapVariable;

/**
 * Standard implementation of DynapResult
 * 
 * @author xbuffat
 *
 */
public class DynapResultImpl implements DynapResult {

	Map<MadxDynapVariable,List<Double>> data;
	
	public DynapResultImpl() {
		this.data = new HashMap<MadxDynapVariable,List<Double>>();
		for(MadxDynapVariable var : MadxDynapVariable.values())
		{
			this.data.put(var, new ArrayList<Double>());
		}
	}
	
	@Override
	public ResultType getResultType() {
		return ResultType.DYNAP_RESULT;
	}

	@Override
	public void add(MadxDynapVariable var, Double value) {
		this.data.get(var).add(value);
		
	}

	@Override
	public void clear() {
		for(MadxDynapVariable var : MadxDynapVariable.values())
		{
			this.data.get(var).clear();
		}
	}

	@Override
	public List<Double> get(MadxDynapVariable variable) {
		return this.data.get(variable);
	}

}
