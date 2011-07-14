// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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