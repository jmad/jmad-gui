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
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.gui.components.DoubleTableCellRenderer;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelListener;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.util.gui.menu.ActionProvider;
import cern.accsoft.steering.util.gui.menu.Checkable;
import cern.accsoft.steering.util.gui.menu.MousePopupListener;
import cern.accsoft.steering.util.gui.menu.TablePopupMenu;
import cern.accsoft.steering.util.gui.menu.ValueSetable;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;
import cern.accsoft.steering.util.gui.table.SelectionSetTableModel;
import cern.accsoft.steering.util.gui.table.TableModelSelectionAdapter;

/**
 * this class represents the panel, which allows to view all strengths of a
 * model (that is the content of the strength-file) and in addition it provides
 * the possibility to select certain strengths (if a EditHandler is set).
 * 
 * @author kfuchsbe
 */
public abstract class AbstractKnobsPanel extends JPanel implements
		EditHandlerUser<KnobsPanelEditHandler> {
	private static final long serialVersionUID = 1733380183463632497L;

	/**
	 * the edit handler, which is responsible for selecting/deselecting certain
	 * strenghtes.
	 */
	private KnobsPanelEditHandler editHandler;

	/** the table model for the display */
	private ModelStrengthesTableModel tableModel;

	/** the actual model */
	private JMadModel model;

	/** an action provider for the context menu */
	private ActionProvider actionProvider;

	/**
	 * the constructor
	 */
	public AbstractKnobsPanel() {
		super(new BorderLayout());
	}

	public void init() {
		initComponents();

		if (this.model != null) {
			refreshDisplay();
		}
	}

	/**
	 * Setter method for edit - handler used for DI
	 * 
	 * @param editHandler
	 */
	@Override
	public void setEditHandler(TablePanelEditHandler editHandler) {
		if (editHandler instanceof KnobsPanelEditHandler) {
			this.editHandler = (KnobsPanelEditHandler) editHandler;
		}
	}

	/**
	 * set the model-manager which determines if e.g. the model changed.
	 * 
	 * @param modelManager
	 *            the modelManager to set.
	 */
	public void setModelManager(JMadModelManager modelManager) {
		modelManager.addListener(new JMadModelManagerAdapter() {

			@Override
			public void changedActiveModel(JMadModel newModel) {
				setModel(newModel);
				refreshDisplay();
			}
		});

		if (modelManager.getActiveModel() != null) {
			setModel(modelManager.getActiveModel());
		}
	}

	protected void refreshDisplay() {
		if (tableModel != null) {
			tableModel.fireTableDataChanged();
		}
	}

	/**
	 * initializes all components
	 */
	private void initComponents() {
		tableModel = new ModelStrengthesTableModel();

		JTable table = new JTable(tableModel);
		table.setDefaultRenderer(Double.class, new DoubleTableCellRenderer());
		table.setAutoCreateRowSorter(true);
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		new MousePopupListener(table, new TablePopupMenu(tableModel));
		tableModel
				.setTableModelSelectionAdapter(new TableModelSelectionAdapter(
						table));

		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		add(new TableFilterPanel(table), BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * this inner class is the Table model for the displayed Table.
	 * 
	 * @author kfuchsbe
	 */
	class ModelStrengthesTableModel extends SelectionSetTableModel implements
			ValueSetable, Checkable, ActionProvider {
		private final static int COLUMN_COUNT = 4;

		private final static int COL_SELECT = 0;
		private final static int COL_NAME = 1;
		private final static int COL_VALUE = 2;
		private final static int COL_COMMENT = 3;

		private static final long serialVersionUID = 1L;

		@Override
		public int getColumnCount() {
			return COLUMN_COUNT;
		}

		@Override
		public int getRowCount() {
			List<? extends Knob> knobs = getKnobs();
			if (knobs != null) {
				return knobs.size();
			} else {
				return 0;
			}
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col == COL_SELECT) {
				return (editHandler != null);
			} else if (col == COL_VALUE) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Object getValueAt(int row, int col) {
			List<? extends Knob> knobs = getKnobs();

			if (knobs == null) {
				return null;
			}

			Knob knob = knobs.get(row);
			switch (col) {
			case COL_SELECT:
				if (editHandler != null) {
					return editHandler.getSelectionValue(knob);
				} else {
					return false;
				}
			case COL_NAME:
				return knob.getName();
			case COL_VALUE:
				return knob.getValue();
			case COL_COMMENT:
				return knob.getDescription();
			default:
				return null;
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			List<? extends Knob> knobs = getKnobs();
			if (knobs == null) {
				return;
			}

			Knob modelValue = knobs.get(row);
			switch (col) {
			case COL_SELECT:
				if (editHandler != null) {
					editHandler.setSelectionValue(modelValue, (Boolean) value);
				}
				break;
			case COL_VALUE:
				modelValue.setValue((Double) value);
				break;
			}
		}

		@Override
		public Class<?> getColumnClass(int col) {
			switch (col) {
			case COL_SELECT:
				return Boolean.class;
			case COL_NAME:
				return String.class;
			case COL_VALUE:
				return Double.class;
			case COL_COMMENT:
				return String.class;
			default:
				return null;
			}
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case COL_SELECT:
				return "vary";
			case COL_NAME:
				return "strength name";
			case COL_VALUE:
				return "value";
			case COL_COMMENT:
				return "comment";
			default:
				return null;
			}
		}

		//
		// methods for interface ValueSetable
		//

		@Override
		public boolean isValueSetEnabled() {
			return isMultipleRowSetEnabled();
		}

		@Override
		public void setValueAllSelected(Double value) {
			setValueSelectedRows(value, COL_VALUE);
		}

		@Override
		public void checkAllSelected() {
			setValueSelectedRows(true, COL_SELECT);
		}

		@Override
		public boolean isCheckUncheckEnabled() {
			return isMultipleRowSetEnabled();
		}

		@Override
		public void uncheckAllSelected() {
			setValueSelectedRows(false, COL_SELECT);
		}

		@Override
		public String getValueName() {
			return "strength value";
		}

		@Override
		public List<Action> getActions() {
			if (getActionProvider() != null) {
				return getActionProvider().getActions();
			} else {
				return new ArrayList<Action>();
			}
		}

	}

	/**
	 * must be overriden by subclass
	 * 
	 * @return the knobs that shall be presented
	 */
	public abstract List<? extends Knob> getKnobs();

	/**
	 * must return a model listener, to be able to react on changes in the
	 * model.
	 * 
	 * @return the model listener
	 */
	protected abstract JMadModelListener getModelListener();

	private void setModel(JMadModel model) {
		this.model = model;
		if (model != null) {
			JMadModelListener listener = getModelListener();
			if (listener != null) {
				this.model.addListener(listener);
			}
		}
	}

	protected JMadModel getModel() {
		return model;
	}

	public SelectionSetTableModel getTableModel() {
		return this.tableModel;
	}

	public void setActionProvider(ActionProvider actionProvider) {
		this.actionProvider = actionProvider;
	}

	public ActionProvider getActionProvider() {
		return actionProvider;
	}

}
