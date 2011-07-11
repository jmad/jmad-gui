package cern.accsoft.steering.jmad.gui.panels.var;

import cern.accsoft.steering.jmad.domain.var.enums.PtcTwissVariable;

public class PtcTwissVarSelectionTableModel extends GenericTwissVarSelectionTableModel<PtcTwissVariable> {
    private static final long serialVersionUID = 1L;
    
    public PtcTwissVarSelectionTableModel() {
        super(PtcTwissVariable.class);
    }
}
