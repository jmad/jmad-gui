package cern.accsoft.steering.util.gui.table;

import javax.swing.table.AbstractTableModel;

/**
 * this class enables the table-model to keep track of all selected rows and
 * provides a method for subclasses to set a value to all selected rows.
 * 
 * @author kfuchsbe
 * 
 */
public abstract class SelectionSetTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5682220710157369381L;

	/* keeps track of the selected Rows in the TableModel */
	private TableModelSelectionAdapter tableModelSelectionAdapter;

	/**
	 * @return the tableModelSelectionAdapter
	 */
	public final TableModelSelectionAdapter getTableModelSelectionAdapter() {
		return tableModelSelectionAdapter;
	}

	/**
	 * setter used for DI
	 * 
	 * @param tableModelSelectionAdapter
	 *            the {@link TableModelSelectionAdapter} to set
	 */
	public final void setTableModelSelectionAdapter(
			TableModelSelectionAdapter tableModelSelectionAdapter) {
		this.tableModelSelectionAdapter = tableModelSelectionAdapter;
	}

	/**
	 * sets the given value in a given column to all selected Rows.
	 * 
	 * @param value
	 *            the value to set
	 * @param col
	 *            the column to set the value to
	 */
	protected final void setValueSelectedRows(Object value, int col) {
		if (tableModelSelectionAdapter == null) {
			return;
		}

		if (value != null) {
			for (Integer i : tableModelSelectionAdapter.getSelectedRowIndizes()) {
				if (isCellEditable(i, col)) {
					setValueAt(value, i, col);
				}
			}
			fireTableDataChanged();
		}
	}

	/**
	 * @return true if multiple set makes sense, false if not
	 */
	protected final boolean isMultipleRowSetEnabled() {
		return ((tableModelSelectionAdapter != null) && (tableModelSelectionAdapter
				.getSelectedRowIndizes().size() > 0));
	}

}