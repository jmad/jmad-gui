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
package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * This listener can be attached to an element selection manager and gets
 * notified when the selected elements change.
 * 
 * @author kaifox
 * 
 */
public interface ElementSelectionManagerListener {

	/**
	 * This method is called, when the selected elements changed
	 * 
	 * @param selectedElements
	 *            all the currently selected elements in the order as they
	 *            appear in the sequence.
	 * @param lastSelectedElement
	 *            The element which was selected mostResently
	 */
	public void changedSelectedElements(List<Element> selectedElements,
			Element lastSelectedElement);

}
