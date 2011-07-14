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
 * this interface can be implemented by a tableModel to enable the setting of
 * double-values via popup menu.
 * 
 * @author kfuchsbe
 * 
 */
public interface ValueSetable {

	/**
	 * is queried by the popup menu to determine, if it shall enable the
	 * setValue-functionality or not.
	 * 
	 * @return true if enabled, false if not
	 */
	public boolean isValueSetEnabled();
	
	/**
	 * sets the Value to all selected rows.
	 * 
	 * @param value the value to set.
	 */
	public void setValueAllSelected(Double value);
	
	
	/**
	 * has to return the name of the value in order to display it for the user.
	 * 
	 * @return the name of the value
	 */
	public String getValueName();
}
