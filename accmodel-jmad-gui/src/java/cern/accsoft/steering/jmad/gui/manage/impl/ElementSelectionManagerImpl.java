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
package cern.accsoft.steering.jmad.gui.manage.impl;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.gui.manage.AbstractObservableManager;
import cern.accsoft.steering.jmad.gui.manage.ElementSelectionManager;
import cern.accsoft.steering.jmad.gui.manage.ElementSelectionManagerListener;

/**
 * default implementation of the {@link ElementSelectionManager}. It keepos
 * track of all the elements that are currently selected.
 * 
 * @author kaifox
 * 
 */
public class ElementSelectionManagerImpl extends
		AbstractObservableManager<ElementSelectionManagerListener> implements
		ElementSelectionManager {

	/** All the selected elements */
	private List<Element> selectedElements = new ArrayList<Element>();

	/** The most recently selected element */
	private Element selectedElement = null;

	@Override
	public Element getSelectedElement() {
		return this.selectedElement;
	}

	@Override
	public List<Element> getSelectedElements() {
		return this.selectedElements;
	}

	/**
	 * notifies all the listeners that the selected elements have changed.
	 */
	private void notifiyListenersChangedSelectedElements() {
		for (ElementSelectionManagerListener listener : getListeners()) {
			listener.changedSelectedElements(this.selectedElements,
					this.selectedElement);
		}
	}

	@Override
	public void setSelectedElements(List<Element> selectedElements,
			Element selectedElement) {
		this.selectedElements = selectedElements;
		this.selectedElement = selectedElement;
		notifiyListenersChangedSelectedElements();
	}
}
