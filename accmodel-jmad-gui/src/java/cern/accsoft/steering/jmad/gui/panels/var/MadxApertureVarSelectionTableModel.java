package cern.accsoft.steering.jmad.gui.panels.var;

import cern.accsoft.steering.jmad.domain.var.enums.MadxApertureVariable;

public class MadxApertureVarSelectionTableModel extends
		GenericTwissVarSelectionTableModel<MadxApertureVariable> {
	private static final long serialVersionUID = 8877594781559758632L;

	public MadxApertureVarSelectionTableModel() {
		super(MadxApertureVariable.class);
	}

}
