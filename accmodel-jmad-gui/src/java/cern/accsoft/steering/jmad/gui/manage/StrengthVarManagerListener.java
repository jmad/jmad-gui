/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;

import cern.accsoft.steering.jmad.domain.var.custom.CustomVariable;

/**
 * This interface represents a listener to a {@link StrengthVarManager}
 * 
 * @author kfuchsbe
 * 
 */
public interface StrengthVarManagerListener {

	/**
	 * fired when the variables changed
	 * 
	 * @param newVariables
	 *            the new variables
	 */
	public void changedVariables(List<CustomVariable> newVariables);
}
