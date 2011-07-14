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
package cern.accsoft.steering.jmad.gui.mark;

import java.util.Collection;

/**
 * A manager for markers in Charts
 * 
 * @author kfuchsbe
 * 
 */
public interface MarkedElementsManager {

	/**
	 * adds an the given elementName to the marked elements
	 * 
	 * @param marker
	 */
	public void addElementName(String elementName);

	/**
	 * removes a elementName from the list of marked elements
	 * 
	 * @param elementName
	 *            the name of the element to remove
	 */
	public void removeElementName(String elementName);

	/**
	 * @param elementName
	 * @return true, if the manager contains the given name, false otherwise
	 */
	public boolean contains(String elementName);

	/**
	 * @return all contained elementNames
	 */
	public Collection<String> getElementNames();

	/**
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(MarkedElementsManagerListener listener);

	/**
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(MarkedElementsManagerListener listener);
}
