// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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