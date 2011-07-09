package cern.accsoft.steering.jmad.gui.panels.var;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * This is the interface of a class that collects selected variables for one
 * axis.
 * 
 * @author kaifox
 * 
 */
public interface SelectedVarManager extends MadxVarSelector {

	/**
	 * adds a new variable
	 * 
	 * @param twissVariable
	 *            the variable to add
	 */
	public void add(TwissVariable twissVariable);

	/**
	 * remove the given variable
	 * 
	 * @param twissVariable
	 *            the variable to remove
	 */
	public void remove(TwissVariable twissVariable);

	/**
	 * clears the variables
	 */
	public void clear();

	/**
	 * defines, if one or more variables may be selected.
	 * 
	 * @param varSelectionMode
	 */
	public void setVarSelectionMode(VarSelectionMode varSelectionMode);
	
	/**
	 * @return the actual selection mode
	 */
	public VarSelectionMode getVarSelectionMode();

	/**
	 * this mode represents, if only one variable can be selected, or multiple
	 * ones.
	 * 
	 * @author kaifox
	 * 
	 */
	public static enum VarSelectionMode {
		SINGLE, MULTIPLE;
	}
}
