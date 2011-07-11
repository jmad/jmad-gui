package cern.accsoft.steering.jmad.gui.mark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * the implementation for managing elements, that shall be displayed as markers
 * in charts.
 * 
 * @author kfuchsbe
 * 
 */
public class MarkedElementsManagerImpl implements MarkedElementsManager {

	/** this set contains all the names */
	private Set<String> elementNames = new HashSet<String>();

	/** all the listeners to this manager */
	private List<MarkedElementsManagerListener> listeners = new ArrayList<MarkedElementsManagerListener>();

	@Override
	public void addElementName(String elementName) {
		this.elementNames.add(elementName);
		fireAddedElementName(elementName);
	}

	@Override
	public boolean contains(String elementName) {
		return this.elementNames.contains(elementName);
	}

	@Override
	public Collection<String> getElementNames() {
		return this.elementNames;
	}

	@Override
	public void removeElementName(String elementName) {
		this.elementNames.remove(elementName);
		fireRemovedElementName(elementName);
	}

	/**
	 * notify all listeners, that an elementName has been added
	 * 
	 * @param elementName
	 *            the elementName that has been added
	 */
	private void fireAddedElementName(String elementName) {
		for (MarkedElementsManagerListener listener : listeners) {
			listener.addedElementName(elementName);
		}
	}

	/**
	 * notifies all listeners, that an elemntNames has been removed
	 * 
	 * @param elementName
	 *            the name of the element that has beeen removed.
	 */
	private void fireRemovedElementName(String elementName) {
		for (MarkedElementsManagerListener listener : listeners) {
			listener.removedElementName(elementName);
		}
	}

	@Override
	public void addListener(MarkedElementsManagerListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(MarkedElementsManagerListener listener) {
		this.listeners.remove(listener);
	}

}
