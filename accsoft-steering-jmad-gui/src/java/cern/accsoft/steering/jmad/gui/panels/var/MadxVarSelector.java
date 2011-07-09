/**
 * 
 */
package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.Collection;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * The interface of a class that can select variables for plotting.
 * 
 * @author kaifox
 * 
 */
public interface MadxVarSelector {

	/**
	 * This method has to return all the selected variables
	 * 
	 * @return all the selected variables.
	 */
	public Collection<TwissVariable> getSelectedVariables();

	/**
	 * This is a convenient method to get the selected variable when in
	 * single-variable selection mode.
	 * 
	 * @return returns the selected variable.
	 */
	public TwissVariable getSelectedVariable();

}
