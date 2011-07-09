/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage;

import java.io.File;

import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSet;

/**
 * This interface represents a manager, which keeps track of certain variables
 * which can used in twiss-tables.
 * 
 * @author kfuchsbe
 * 
 */
public interface StrengthVarManager {

	/**
	 * Replaces the actual variables with the ones from the file.
	 * 
	 * @param file
	 *            the file from which to load the variables.
	 */
	public void load(File file);

	/**
	 * @return all the actually available variables
	 */
	public StrengthVarSet getStrengthVarSet();

	/**
	 * add a listener
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addListener(StrengthVarManagerListener listener);

	/**
	 * remove a listener
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(StrengthVarManagerListener listener);

}
