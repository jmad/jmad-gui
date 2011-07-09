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
