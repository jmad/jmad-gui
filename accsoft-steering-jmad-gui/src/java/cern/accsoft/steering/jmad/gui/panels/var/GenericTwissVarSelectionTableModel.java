package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.VariableUtil;
import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * table model for the available variables in tfs-results.
 * 
 * @author kfuchsbe
 * 
 */
public class GenericTwissVarSelectionTableModel<T extends Enum<T> & TwissVariable>
		extends AbstractVarSelectionTableModel {
	private static final long serialVersionUID = -4113863768028872933L;

	/** All the variables that can be selected in the panel */
	private final List<T> availableVariables;

	public GenericTwissVarSelectionTableModel(Class<T> clazz) {
		this.availableVariables = new ArrayList<T>(VariableUtil
				.findFromVarType(clazz, MadxVarType.DOUBLE));
	}

	private int columnCount = axesCount + 1;
	private int colidxName = columnCount - 1;
	{
		updateColumnCounts();
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public int getRowCount() {
		return this.getAvailableVariables().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		TwissVariable variable = this.getAvailableVariables().get(row);
		if ((col >= 0) & (col < axesCount)) {
			return getSelectedVarManager().getSelectedVariables().contains(
					variable);
		} else if (col == colidxName) {
			return variable.toString();
		} else {
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int col) {
		if ((col >= 0) & (col < axesCount)) {
			return Boolean.class;
		} else if (col == colidxName) {
			return String.class;
		} else {
			return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		if ((col >= 0) & (col < axesCount)) {
			return "plot";

		} else if (col == colidxName) {
			return "variable";
		} else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if ((col >= 0) & (col < axesCount)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		TwissVariable variable = this.getAvailableVariables().get(row);
		if ((col >= 0) & (col < axesCount)) {
			if ((Boolean) value) {
				getSelectedVarManager().add(variable);
			} else {
				getSelectedVarManager().remove(variable);
			}
			fireTableDataChanged();
		}
	}

	@Override
	protected void updateColumnCounts() {
		columnCount = axesCount + 1;
		colidxName = columnCount - 1;
	}

	@Override
	public List<? extends TwissVariable> getAvailableVariables() {
		return this.availableVariables;
	}

}