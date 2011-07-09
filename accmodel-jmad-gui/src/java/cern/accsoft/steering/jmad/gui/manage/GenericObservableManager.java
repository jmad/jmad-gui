/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage;

/**
 * The generic interface for a class to which listeners can be attached.
 * 
 * @param T
 *            the type of the listener that can be attached to the class.
 * @author kaifox
 * 
 */
public interface GenericObservableManager<T> {

	/**
	 * adds the listener to the class
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(T listener);

	/**
	 * removes a listener from the class
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(T listener);
}
