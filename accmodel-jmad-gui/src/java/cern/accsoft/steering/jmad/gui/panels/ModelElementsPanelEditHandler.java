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
package cern.accsoft.steering.jmad.gui.panels;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * defines methods which are called by ModelElementsPanel for using an
 * additional - selection column.
 * 
 * @author kfuchsbe
 * 
 */
public interface ModelElementsPanelEditHandler extends TablePanelEditHandler {

	/**
	 * @param element
	 * @param attributeName
	 * @return true if the given attribute of the given element is selected.
	 */
	public Boolean getSelectionValue(Element element, String attributeName);

	/**
	 * sets the attribute of the element as selected / deselected
	 * 
	 * @param element
	 *            the element, for which to set the attribute as selected
	 * @param attributeName
	 *            the attributName to select
	 * @param value
	 *            true for select, false for deselect
	 */
	public void setSelectionValue(Element element, String attributeName,
			boolean value);
}
