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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.gui.panels.var.SelectedVarManager.VarSelectionMode;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;
import cern.accsoft.steering.util.table.RadioColumnEditor;
import cern.accsoft.steering.util.table.RadioColumnRenderer;

public class VarSelectionPanel extends JPanel {
	private static final long serialVersionUID = 2433227096109178284L;

	/** the size of the table */
	final static Dimension PREFERRED_TABLE_SIZE = new Dimension(200, 400);

	private SelectedVarManager selectedVarManager = new SelectedVarManagerImpl();

	private TwissVariable defaultVariable = null;

	private JTable table;

	private AbstractVarSelectionTableModel tableModel;

	public void init() {
		initComponents();
	}

	/**
	 * initializes the components.
	 */
	protected void initComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weighty = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.BOTH;

		this.getTableModel().setShowSelectColumn(true);
		this.getTableModel().setSelectedVarManager(getSelectedVarManager());

		table = new JTable(this.getTableModel());
		table.setAutoCreateRowSorter(true);
		add(new TableFilterPanel(table), constraints);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumn selectColumn = table.getColumnModel().getColumn(0);
		selectColumn.setPreferredWidth(50);
		if (VarSelectionMode.SINGLE == getSelectedVarManager()
				.getVarSelectionMode()) {
			selectColumn.setCellRenderer(new RadioColumnRenderer());
			selectColumn.setCellEditor(new RadioColumnEditor());
		}

		if (getDefaultVariable() != null) {
			for (int i = 0; i < getTableModel().getAvailableVariables().size(); i++) {
				TwissVariable var = getTableModel().getAvailableVariables()
						.get(i);
				if (this.getDefaultVariable().equals(var)) {
					getTableModel().setValueAt(true, i, 0);
				}
			}
		}

		constraints.gridy++;
		constraints.weightx = 1;
		constraints.weighty = 1;
		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(PREFERRED_TABLE_SIZE);
		add(scrollPane, constraints);

		validate();
	}

	public void setTableModel(AbstractVarSelectionTableModel tableModel) {
		this.tableModel = tableModel;
	}

	private AbstractVarSelectionTableModel getTableModel() {
		return tableModel;
	}

	public TwissVariable getDefaultVariable() {
		return defaultVariable;
	}

	public void setDefaultVariable(TwissVariable defaultVariable) {
		this.defaultVariable = defaultVariable;
	}

	public void setSelectedVarManager(SelectedVarManager selectedVarManager) {
		this.selectedVarManager = selectedVarManager;
	}

	private SelectedVarManager getSelectedVarManager() {
		return selectedVarManager;
	}

}