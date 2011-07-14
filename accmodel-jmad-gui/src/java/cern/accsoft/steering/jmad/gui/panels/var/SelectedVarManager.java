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
package cern.accsoft.steering.jmad.gui.panels.var;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * This is the interface of a class that collects selected variables for one
 * axis.
 * 
 * @author kaifox
 * 
 */
public interface SelectedVarManager extends MadxVarSelector {

	/**
	 * adds a new variable
	 * 
	 * @param twissVariable
	 *            the variable to add
	 */
	public void add(TwissVariable twissVariable);

	/**
	 * remove the given variable
	 * 
	 * @param twissVariable
	 *            the variable to remove
	 */
	public void remove(TwissVariable twissVariable);

	/**
	 * clears the variables
	 */
	public void clear();

	/**
	 * defines, if one or more variables may be selected.
	 * 
	 * @param varSelectionMode
	 */
	public void setVarSelectionMode(VarSelectionMode varSelectionMode);
	
	/**
	 * @return the actual selection mode
	 */
	public VarSelectionMode getVarSelectionMode();

	/**
	 * this mode represents, if only one variable can be selected, or multiple
	 * ones.
	 * 
	 * @author kaifox
	 * 
	 */
	public static enum VarSelectionMode {
		SINGLE, MULTIPLE;
	}
}
