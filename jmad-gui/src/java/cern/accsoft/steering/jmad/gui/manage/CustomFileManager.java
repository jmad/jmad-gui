/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage;

import java.io.File;
import java.util.List;

/**
 * This is the interface of a class which keeps track of custom files, that can
 * be added and executed.
 * 
 * @author kfuchsbe
 * 
 */
public interface CustomFileManager {

	/**
	 * adds a file to the manager
	 * 
	 * @param file
	 *            the file to add
	 */
	public void add(File file);

	/**
	 * removes a file from the manager.
	 * 
	 * @param file
	 *            the file to remove
	 */
	public void remove(File file);

	/**
	 * @return all the currently available files
	 */
	public List<File> getFiles();

	/**
	 * adds a listener to the {@link CustomFileManager}
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(CustomFileManagerListener listener);

	/**
	 * removes a listener from the {@link CustomFileManager}
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(CustomFileManagerListener listener);

}
