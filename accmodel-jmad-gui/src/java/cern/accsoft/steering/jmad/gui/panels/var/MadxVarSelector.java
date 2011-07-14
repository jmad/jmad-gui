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
package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.Collection;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * The interface of a class that can select variables for plotting.
 * 
 * @author kaifox
 * 
 */
public interface MadxVarSelector {

	/**
	 * This method has to return all the selected variables
	 * 
	 * @return all the selected variables.
	 */
	public Collection<TwissVariable> getSelectedVariables();

	/**
	 * This is a convenient method to get the selected variable when in
	 * single-variable selection mode.
	 * 
	 * @return returns the selected variable.
	 */
	public TwissVariable getSelectedVariable();

}
