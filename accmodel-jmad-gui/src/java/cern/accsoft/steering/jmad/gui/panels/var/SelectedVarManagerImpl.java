package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * the simplest implementation of a class collecting variables.
 * 
 * @author kaifox
 * 
 */
public class SelectedVarManagerImpl implements SelectedVarManager {

	/** The logger for the class */
	private final static Logger logger = Logger
			.getLogger(SelectedVarManagerImpl.class);

	/** how many variables may be selected */
	private VarSelectionMode varSelectionMode = VarSelectionMode.MULTIPLE;

	/** the variables */
	private List<TwissVariable> variables = new ArrayList<TwissVariable>();

	@Override
	public void add(TwissVariable twissVariable) {
		if (VarSelectionMode.SINGLE == this.varSelectionMode) {
			this.clear();
		}
		this.variables.add(twissVariable);
	}

	@Override
	public void clear() {
		this.variables.clear();
	}

	@Override
	public Collection<TwissVariable> getSelectedVariables() {
		return this.variables;
	}

	@Override
	public void remove(TwissVariable twissVariable) {
		if (VarSelectionMode.SINGLE == this.varSelectionMode) {
			return;
		}
		this.variables.remove(twissVariable);
	}

	@Override
	public void setVarSelectionMode(VarSelectionMode varSelectionMode) {
		this.varSelectionMode = varSelectionMode;
	}

	@Override
	public TwissVariable getSelectedVariable() {
		if (VarSelectionMode.SINGLE != this.varSelectionMode) {
			logger.warn("This only makes sense in single selection mode!");
			return null;
		}
		if (variables.size() == 1) {
			return variables.get(0);
		} else {
			return null;
		}
	}

	@Override
	public VarSelectionMode getVarSelectionMode() {
		return this.varSelectionMode;
	}

}
