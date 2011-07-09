/*
 * $Id: TfsDataSetManagerPanel.java,v 1.4 2009-03-16 16:36:33 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:36:33 $ 
 * $Revision: 1.4 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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
