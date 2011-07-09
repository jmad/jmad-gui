package cern.accsoft.steering.jmad.gui.panels.var;

import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public class MadxTwissVarSelectionTableModel extends
		GenericTwissVarSelectionTableModel<MadxTwissVariable> {
	private static final long serialVersionUID = 7570157686888341311L;

	public MadxTwissVarSelectionTableModel() {
		super(MadxTwissVariable.class);
	}

}
