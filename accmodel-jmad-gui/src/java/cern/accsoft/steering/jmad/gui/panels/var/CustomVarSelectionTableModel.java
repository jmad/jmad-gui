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
package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.custom.CustomVariable;
import cern.accsoft.steering.jmad.gui.manage.StrengthVarManager;
import cern.accsoft.steering.jmad.gui.manage.StrengthVarManagerListener;

/**
 * This panel allows to parse a variable-file and add these variables to the
 * twiss results.
 * 
 * @author kfuchsbe
 * 
 */
public class CustomVarSelectionTableModel extends
		AbstractVarSelectionTableModel {

	private static final long serialVersionUID = 1630862227442352343L;

	/** the {@link StrengthVarManager} */
	private StrengthVarManager strengthVarManager = null;

	private int columnCount;
	{
		updateColumnCounts();
	}

	private final static int COL_NAME = 0;
	private final static int COL_EXPRESSION = 1;
	private final static int COL_COMMENT = 2;

	/** All the variables to display */
	private List<CustomVariable> variables = new ArrayList<CustomVariable>();

	/**
	 * method used by spring to inject the {@link StrengthVarManager}
	 * 
	 * @param variableFileManager
	 *            the {@link StrengthVarManager} to set
	 */
	public void setVariableFileManager(StrengthVarManager variableFileManager) {
		this.strengthVarManager = variableFileManager;
		this.strengthVarManager.addListener(new StrengthVarManagerListener() {

			@Override
			public void changedVariables(List<CustomVariable> newVariables) {
				variables = newVariables;
				fireTableDataChanged();
			}
		});
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public int getRowCount() {
		return variables.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		CustomVariable variable = variables.get(row);
		switch (col - axesCount) {
		case -1:
			return getSelectedVarManager().getSelectedVariables().contains(
					variable);
		case COL_NAME:
			return variable.getMadxName();
		case COL_EXPRESSION:
			return variable.getExpression();
		case COL_COMMENT:
			return variable.getComment();
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int col) {
		switch (col - axesCount) {
		case -1:
			return Boolean.class;
		case COL_NAME:
		case COL_EXPRESSION:
		case COL_COMMENT:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		switch (col - axesCount) {
		case -1:
			return "plot";
		case COL_NAME:
			return "variable";
		case COL_EXPRESSION:
			return "expression";
		case COL_COMMENT:
			return "comment";
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if ((axesCount > 0) && (col == 0)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		CustomVariable variable = variables.get(row);
		switch (col - axesCount) {
		case -1:
			if ((Boolean) value) {
				getSelectedVarManager().add(variable);
			} else {
				getSelectedVarManager().remove(variable);
			}
			fireTableDataChanged();
			break;
		}
		
	}

	@Override
	public List<? extends TwissVariable> getAvailableVariables() {
		return variables;
	}

	@Override
	protected void updateColumnCounts() {
		columnCount = axesCount + 3;
	}

}
