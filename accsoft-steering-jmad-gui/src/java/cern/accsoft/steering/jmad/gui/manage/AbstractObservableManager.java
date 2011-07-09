package cern.accsoft.steering.jmad.gui.manage;

import java.util.ArrayList;
import java.util.List;

/**
 * the base implementation of a class to which listeners can be attached
 * 
 * @author kaifox
 * 
 * @param <T>
 *            the type of listeners that can be attached
 */
public class AbstractObservableManager<T> implements
		GenericObservableManager<T> {

	/** all the listeners to this class. */
	private List<T> listeners = new ArrayList<T>();

	@Override
	public void addListener(T listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(T listener) {
		this.listeners.remove(listener);
	}

	protected List<T> getListeners() {
		return this.listeners;
	}

}
