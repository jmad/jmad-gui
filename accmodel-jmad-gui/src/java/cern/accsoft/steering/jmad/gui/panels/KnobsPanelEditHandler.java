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

import cern.accsoft.steering.jmad.domain.knob.Knob;

/**
 * provides methods to handle the selection of certain strengthes in a
 * {@link AbstractKnobsPanel}
 * 
 * @author kfuchsbe
 * 
 */
public interface KnobsPanelEditHandler extends TablePanelEditHandler {

	/**
	 * @param value
	 * @return true, if the strength is selected, false, if not
	 */
	public boolean getSelectionValue(Knob value);

	/**
	 * sets the strength as selected or unselected, accordiog to the given value
	 * 
	 * @param modelValue
	 *            the strength for which to modify the selection - value
	 * @param value
	 *            true: select the strength, false: deselect
	 */
	public void setSelectionValue(Knob modelValue, boolean value);
}
