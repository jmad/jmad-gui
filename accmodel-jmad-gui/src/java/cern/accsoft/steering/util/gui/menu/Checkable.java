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
package cern.accsoft.steering.util.gui.menu;

/**
 * this interface can be implemented by a TableModel in order to receive
 * commands from the popu menu.
 * 
 * @author kfuchsbe
 * 
 */
public interface Checkable {

	/**
	 * queried to determine, if to enable/disable menu-items
	 * 
	 * @return if check/uncheck items should be enabled
	 */
	public boolean isCheckUncheckEnabled();
	
	/**
	 * tells the TableModel to check all selected rows
	 */
	public void checkAllSelected();
	
	
	/**
	 * tells the TableModel to uncheck all selected rows
	 */
	public void uncheckAllSelected();
}
