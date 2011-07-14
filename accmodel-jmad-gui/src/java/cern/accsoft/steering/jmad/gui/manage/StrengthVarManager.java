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
package cern.accsoft.steering.jmad.gui.manage;

import java.io.File;

import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSet;

/**
 * This interface represents a manager, which keeps track of certain variables
 * which can used in twiss-tables.
 * 
 * @author kfuchsbe
 * 
 */
public interface StrengthVarManager {

	/**
	 * Replaces the actual variables with the ones from the file.
	 * 
	 * @param file
	 *            the file from which to load the variables.
	 */
	public void load(File file);

	/**
	 * @return all the actually available variables
	 */
	public StrengthVarSet getStrengthVarSet();

	/**
	 * add a listener
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(StrengthVarManagerListener listener);

	/**
	 * remove a listener
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(StrengthVarManagerListener listener);

}
