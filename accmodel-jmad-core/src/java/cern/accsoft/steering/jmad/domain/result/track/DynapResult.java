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

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.var.enums.MadxDynapVariable;

/**
 * 
 * This interface allow to manage informations contained in the output of a dynap task.
 * Information in the summary are discarded.
 * 
 * @author xbuffat
 *
 */

public interface DynapResult extends Result{

	/**
	 * clear the result
	 */
	void clear();

	/**
	 * store a value associated to the variable
	 * 
	 * @param variable
	 * @param value
	 */
	void add(MadxDynapVariable variable, Double value);
	
	
	/**
	 * retrieve data associated to the variable.
	 * 
	 * @param variable
	 * @return all values for the variable
	 */
	 List<Double> get(MadxDynapVariable variable);

}
