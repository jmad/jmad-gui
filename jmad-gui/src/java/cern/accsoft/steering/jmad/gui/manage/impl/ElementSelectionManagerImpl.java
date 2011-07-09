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
