package cern.accsoft.steering.jmad.gui.panels.var;

import javax.swing.table.AbstractTableModel;

import cern.accsoft.steering.jmad.gui.manage.VariableProvider;

public abstract class AbstractVarSelectionTableModel extends AbstractTableModel implements VariableProvider {
    private static final long serialVersionUID = -2384256307499536767L;

    /** the variables selected in the table */
    private SelectedVarManager selectedVarManager = new SelectedVarManagerImpl();

    private boolean showSelectColumn;

    protected int axesCount = 0;

    protected boolean isShowSelectColumn() {
        return showSelectColumn;
    }

    public void setShowSelectColumn(boolean showSelectColumn) {
        this.showSelectColumn = showSelectColumn;
        if (this.showSelectColumn) {
            axesCount = 1;
        } else {
            axesCount = 0;
        }
        updateColumnCounts();
    }

    protected abstract void updateColumnCounts();

    public void setSelectedVarManager(SelectedVarManager selectedVarManager) {
        this.selectedVarManager = selectedVarManager;
    }

    protected SelectedVarManager getSelectedVarManager() {
        return selectedVarManager;
    }
}