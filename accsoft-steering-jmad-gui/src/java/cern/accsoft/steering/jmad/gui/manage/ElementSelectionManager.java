/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * Keeps track of all actually selected elements of a model. Each gui element
 * which wants to change the selected elements can do this with the provide
 * methods. Elements which want to get notified on changes can add a listener to
 * this class.
 * 
 * @author kaifox
 * 
 */
public interface ElementSelectionManager extends
		GenericObservableManager<ElementSelectionManagerListener> {

	/**
	 * returns all the selected elements in the order as they appear in the
	 * sequence. The order is independent of the time when selecting the
	 * individual elements.
	 * 
	 * @return all the actually selected elements
	 */
	public List<Element> getSelectedElements();

	/**
	 * Returns the element which was last selected.
	 * 
	 * @return the most recently selected element
	 */
	public Element getSelectedElement();

	/**
	 * Sets the given elements as the actually selected ones
	 * 
	 * @param selectedElements
	 *            all the selected elements (migth be an empty list)
	 * @param mostRecentlySelectedElement
	 *            the element which was most recently selected. might be
	 *            <code>null</code>
	 */
	public void setSelectedElements(List<Element> selectedElements,
			Element mostRecentlySelectedElement);

}
