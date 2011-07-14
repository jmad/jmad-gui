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
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.JMadElementType;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;
import cern.accsoft.steering.jmad.domain.optics.EditableOpticPointImpl;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.gui.components.DoubleTableCellRenderer;
import cern.accsoft.steering.jmad.gui.manage.ElementSelectionManager;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManager;
import cern.accsoft.steering.jmad.gui.mark.MarkedElementsManagerListener;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelListener;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.util.gui.CompUtils;
import cern.accsoft.steering.util.gui.menu.ActionProvider;
import cern.accsoft.steering.util.gui.menu.MousePopupListener;
import cern.accsoft.steering.util.gui.menu.TablePopupMenu;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;
import cern.accsoft.steering.util.gui.table.BeanTableModel;
import cern.accsoft.steering.util.gui.table.SelectionSetTableModel;
import cern.accsoft.steering.util.gui.table.TableModelSelectionAdapter;

/**
 * this class represents a panel, which enables the display of all elements of a
 * model including the details of the element. It also offers the possibility to
 * select certain properties of some elements (when an Edit Handler is plugged
 * in).
 * 
 * @author kfuchsbe
 */
public class ModelElementsPanel extends JPanel implements
		EditHandlerUser<ModelElementsPanelEditHandler> {
	private static final long serialVersionUID = 1L;

	/** the logger for the class */
	private final static Logger logger = Logger
			.getLogger(ModelElementsPanel.class);

	/**
	 * The initial location of the divider between elements-table and
	 * element-table.
	 */
	private final static int DIVIDER_LOCATION = 250;

	/**
	 * here we can plugin an edithandler to process selection/deselection of
	 * special attributes.
	 */
	private ModelElementsPanelEditHandler editHandler;

	/** The model manager that e.g. determines if the model changes */
	private JMadModelManager modelManager;

	/**
	 * The manager, which keeps track of all elements, that shall be displayed
	 * as markers in the charts
	 */
	private MarkedElementsManager markedElementsManager;

	/**
	 * the manager which keeps track of all selected elements
	 */
	private ElementSelectionManager elementSelectionManager;

	/** the table models for the elements-table and for the element-table. */
	private ModelElementsTableModel tableModelElements = null;
	private ModelElementTableModel tableModelElement = null;
	private BeanTableModel tableModelElementOptic = null;

	/** the text field which displays the name of the actual selected element. */
	private JTextField txtElementName = null;

	/** the listener, which shall be added to every model */
	private JMadModelListener modelListener = new JMadModelListener() {

		@Override
		public void elementsChanged() {
			tableModelElement.fireTableDataChanged();
			validate();
		}

		@Override
		public void opticsChanged() {
			tableModelElementOptic.fireTableDataChanged();
			validate();
		}

		@Override
		public void rangeChanged(Range newRange) {
			tableModelElements.fireTableDataChanged();
			validate();
		}

		@Override
		public void becameDirty() {
			/* for the moment do not update every time a single value changes. */
		}

		@Override
		public void opticsDefinitionChanged() {
			/* for the moment do not update every time a single value changes. */
		}
	};

	/**
	 * the constructor
	 */
	public ModelElementsPanel() {
		super(new BorderLayout());
		initComponents();
	}

	/**
	 * set the model-manager which determines e.g. when the model changes.
	 * 
	 * @param modelManager
	 */
	public final void setModelManager(JMadModelManager modelManager) {
		this.modelManager = modelManager;

		if (this.modelManager.getActiveModel() != null) {
			this.modelManager.getActiveModel().addListener(modelListener);
		}

		modelManager.addListener(new JMadModelManagerAdapter() {

			@Override
			public void changedActiveModel(JMadModel newModel) {
				if (newModel != null) {
					newModel.addListener(modelListener);
				}
				tableModelElements.fireTableDataChanged();
			}

		});
	}

	/**
	 * create all containing Components
	 */
	private final void initComponents() {
		JTable table;

		/*
		 * the panel for the overall list
		 */
		JPanel listPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;

		tableModelElements = new ModelElementsTableModel();
		table = new JTable(tableModelElements);
		table.setDefaultRenderer(Double.class, new DoubleTableCellRenderer());
		table.setAutoCreateRowSorter(true);
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new ElementSelectionAdapter(table));
		tableModelElements
				.setTableModelSelectionAdapter(new TableModelSelectionAdapter(
						table));
		JScrollPane elementsScrollPane = CompUtils.createScrollPane(table);

		/* add a popup-menu to the table */
		new MousePopupListener(table, new TablePopupMenu(
				new MisalignmentCreator()));

		/*
		 * the filter for the elements-table
		 */
		listPanel.add(new TableFilterPanel(table), constraints);

		constraints.weighty = 1;
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.gridwidth = 2;
		listPanel.add(elementsScrollPane, constraints);

		/*
		 * the panel for the details
		 */
		JPanel detailPanel = new JPanel(new GridBagLayout());
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;

		txtElementName = new JTextField();
		txtElementName.setEditable(false);
		detailPanel.add(txtElementName, constraints);

		constraints.weighty = 1;
		constraints.gridy++;
		tableModelElement = new ModelElementTableModel();
		table = new JTable(tableModelElement);
		table.setDefaultRenderer(Double.class, new DoubleTableCellRenderer());
		detailPanel.add(CompUtils.createScrollPane(table), constraints);

		constraints.gridy++;
		tableModelElementOptic = new BeanTableModel(OpticPoint.class);
		table = new JTable(tableModelElementOptic);
		table.setDefaultRenderer(Double.class, new DoubleTableCellRenderer());
		detailPanel.add(CompUtils.createScrollPane(table), constraints);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		splitPane.setLeftComponent(listPanel);
		// splitPane.setLeftComponent(elementsScrollPane);
		splitPane.setRightComponent(detailPanel);
		add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(DIVIDER_LOCATION);
	}

	/**
	 * @return the actually selected elements
	 */
	private ArrayList<Element> getSelectedElements() {
		ArrayList<Element> elements = new ArrayList<Element>();

		if (modelManager != null) {
			Range range = modelManager.getActiveModel().getActiveRange();
			if (range != null) {
				for (Integer index : this.tableModelElements
						.getTableModelSelectionAdapter()
						.getSelectedRowIndizes()) {
					elements.add(range.getElements().get(index));
				}
			}
		}
		return elements;
	}

	private Element getElement(int modelIndex) {
		if ((modelIndex >= 0) && (modelManager != null)) {
			Range range = modelManager.getActiveModel().getActiveRange();
			if (range != null) {
				return range.getElements().get(modelIndex);
			}
		}
		return null;
	}

	/**
	 * this class is attended to be plugged in into the table-menu
	 * 
	 * @author kfuchsbe
	 */
	private class MisalignmentCreator implements ActionProvider {

		/**
		 * this action adds an empty misalignment to the model, for each
		 * selected element.
		 */
		private Action createMisalignmentsAction = new AbstractAction(
				"create misalignments") {
			private static final long serialVersionUID = -7332873258819061907L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				List<Element> elements = getSelectedElements();
				for (Element element : elements) {
					Range range = modelManager.getActiveModel()
							.getActiveRange();
					range.addMisalignment(new MisalignmentConfiguration(element
							.getName()));
				}
			}

		};

		@Override
		public List<Action> getActions() {
			List<Action> actions = new ArrayList<Action>();
			actions.add(this.createMisalignmentsAction);
			return actions;
		}

	}

	/**
	 * This class is the implementation of a listener to change the selected
	 * elements in the elements-table.
	 * 
	 * @author kfuchsbe
	 */
	private class ElementSelectionAdapter implements ListSelectionListener {
		private JTable table = null;

		public ElementSelectionAdapter(JTable table) {
			this.table = table;
		}

		@Override
		public void valueChanged(ListSelectionEvent event) {
			if (event.getSource() == table.getSelectionModel()) {
				int index = table.getSelectedRow();
				Element element = null;
				if (index >= 0) {
					element = getElement(table.convertRowIndexToModel(index));
				}
				displayElementDetails(element);
				if (getElementSelectionManager() != null) {
					getElementSelectionManager().setSelectedElements(
							getSelectedElements(), element);
				}
			}
		}

	}

	/**
	 * This class is the table model for the table of available elements.
	 * 
	 * @author kfuchsbe
	 */
	private class ModelElementsTableModel extends SelectionSetTableModel {
		private static final long serialVersionUID = 1L;

		private final static int COLUMN_COUNT = 4;

		private final static int COL_NUMBER = 0;
		private final static int COL_MARK = 1;
		private final static int COL_NAME = 2;
		private final static int COL_TYPE = 3;

		@Override
		public int getColumnCount() {
			return COLUMN_COUNT;
		}

		@Override
		public int getRowCount() {
			if ((modelManager == null)
					|| (modelManager.getActiveModel() == null)
					|| (modelManager.getActiveModel().getActiveRange() == null)) {
				return 0;
			}
			return getElements().size();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if (col == COL_MARK) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			if (modelManager == null) {
				return;
			}

			if (col == COL_MARK) {
				if (getMarkedElementsManager() == null) {
					return;
				}
				Element element = modelManager.getActiveModel()
						.getActiveRange().getElements().get(row);
				if ((Boolean) value) {
					getMarkedElementsManager()
							.addElementName(element.getName());
				} else {
					getMarkedElementsManager().removeElementName(
							element.getName());
				}
			}
		}

		@Override
		public Object getValueAt(int row, int col) {
			if (modelManager == null) {
				return null;
			}

			Element element = modelManager.getActiveModel().getActiveRange()
					.getElements().get(row);
			switch (col) {
			case COL_NUMBER:
				return row;
			case COL_MARK:
				if (getMarkedElementsManager() == null) {
					return false;
				} else {
					return getMarkedElementsManager().contains(
							element.getName());
				}
			case COL_NAME:
				return element.getName();
			case COL_TYPE:
				return JMadElementType.fromElement(element).toString();
			default:
				return null;
			}
		}

		@Override
		public Class<?> getColumnClass(int col) {
			switch (col) {
			case COL_NUMBER:
				return Integer.class;
			case COL_MARK:
				return Boolean.class;
			case COL_NAME:
				return String.class;
			case COL_TYPE:
				return String.class;
			default:
				return null;
			}
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case COL_NUMBER:
				return "number";
			case COL_MARK:
				return "mark";
			case COL_NAME:
				return "name";
			case COL_TYPE:
				return "type";
			default:
				return null;
			}
		}

		private List<Element> getElements() {
			if ((modelManager != null)
					&& (modelManager.getActiveModel() != null)
					&& (modelManager.getActiveModel().getActiveRange() != null)) {
				return modelManager.getActiveModel().getActiveRange()
						.getElements();
			}
			return new ArrayList<Element>();
		}
	}

	/**
	 * This is the table model for the table of details of one element
	 */
	private class ModelElementTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		/* the name an position are not accessible through property - name */
		private final static int DEFAULT_ATTRIBUTE_COUNT = 1;

		private final static int ROW_POSITION = 0;
		private final static String NAME_POSITIONE = "position";

		/* the columns */
		private final static int COLUMN_COUNT = 3;

		private final static int COL_SELECTED = 0;
		private final static int COL_ATTR_NAME = 1;
		private final static int COL_ATTR_VALUE = 2;

		private Element element = null;

		public void setElement(Element element) {
			this.element = element;
			fireTableDataChanged();
		}

		@Override
		public int getColumnCount() {
			return COLUMN_COUNT;
		}

		@Override
		public int getRowCount() {
			if (element != null) {
				return element.getAttributeNames().size()
						+ DEFAULT_ATTRIBUTE_COUNT;
			} else {
				return 0;
			}
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if ((row != ROW_POSITION) && (col != COL_ATTR_NAME)) {
				return (getValueAt(row, COL_ATTR_VALUE) != null);
			} else {
				return false;
			}
		}

		@Override
		public Object getValueAt(int row, int col) {
			if (element == null) {
				return null;
			}

			switch (col) {
			case COL_ATTR_NAME:
				if (row == ROW_POSITION) {
					return NAME_POSITIONE;
				} else {
					return element.getAttributeNames().get(
							row - DEFAULT_ATTRIBUTE_COUNT);
				}
			case COL_ATTR_VALUE:
				if (row == ROW_POSITION) {
					return element.getPosition().getValue();
				} else {
					return element.getAttribute(element.getAttributeNames()
							.get(row - DEFAULT_ATTRIBUTE_COUNT));
				}
			case COL_SELECTED:
				if (row == ROW_POSITION) {
					return false;
				} else {
					if (editHandler != null) {
						return editHandler.getSelectionValue(element, element
								.getAttributeNames().get(
										row - DEFAULT_ATTRIBUTE_COUNT));
					} else {
						return false;
					}
				}
			default:
				return null;
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			if (row < DEFAULT_ATTRIBUTE_COUNT) {
				return;
			}

			String attributeName = element.getAttributeNames().get(
					row - DEFAULT_ATTRIBUTE_COUNT);
			switch (col) {
			case COL_SELECTED:
				if (editHandler != null) {
					ArrayList<Element> elements = getSelectedElements();
					for (Element elem : elements) {
						editHandler.setSelectionValue(elem, attributeName,
								(Boolean) value);
					}
					fireTableCellUpdated(row, col);
				}
				break;
			case COL_ATTR_VALUE:
				ArrayList<Element> elements = getSelectedElements();
				for (Element elem : elements) {
					elem.setAttribute(attributeName, (Double) value);
				}
				fireTableCellUpdated(row, col);
				break;
			}
		}

		@Override
		public Class<?> getColumnClass(int col) {
			switch (col) {
			case COL_ATTR_NAME:
				return String.class;
			case COL_ATTR_VALUE:
				return Double.class;
			case COL_SELECTED:
				return Boolean.class;
			default:
				return null;
			}
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case COL_ATTR_NAME:
				return "attribute name";
			case COL_ATTR_VALUE:
				return "value";
			case COL_SELECTED:
				if (editHandler != null) {
					return editHandler.getCheckableColumnHeader();
				} else {
					return "selected";
				}
			default:
				return null;
			}
		}
	}

	/**
	 * displays the data of the given element.
	 * 
	 * @param element
	 *            the element whose data to display
	 */
	private void displayElementDetails(Element element) {
		if (element != null) {
			txtElementName.setText(element.getName());
			tableModelElement.setElement(element);
			tableModelElementOptic.setBean(getElementOptics(element));
		} else {
			txtElementName.setText("");
			tableModelElement.setElement(null);
			tableModelElementOptic.setBean(null);
		}
	}

	/**
	 * retrieves the optics-point for an element from the model and handles
	 * exceptions if they occur.
	 * 
	 * @param element
	 *            the element for which to get the optics point
	 * @return the {@link EditableOpticPointImpl}
	 */
	private OpticPoint getElementOptics(Element element) {
		JMadModel model = modelManager.getActiveModel();
		try {
			return model.getOptics().getPoint(element);
		} catch (JMadModelException e) {
			logger.warn("no optics-information found for element '"
					+ element.getName() + "'", e);
			return null;
		}
	}

	/**
	 * @return the editHandler
	 */
	public final ModelElementsPanelEditHandler getEditHandler() {
		return editHandler;
	}

	/**
	 * @param editHandler
	 *            the editHandler to set
	 */
	@Override
	public final void setEditHandler(TablePanelEditHandler editHandler) {
		if (editHandler instanceof ModelElementsPanelEditHandler) {
			this.editHandler = (ModelElementsPanelEditHandler) editHandler;
		}
	}

	/**
	 * @param markedElementsManager
	 *            the markedElementsManager to set
	 */
	public void setMarkedElementsManager(
			MarkedElementsManager markedElementsManager) {
		this.markedElementsManager = markedElementsManager;
		markedElementsManager.addListener(new MarkedElementsManagerListener() {

			@Override
			public void addedElementName(String elementName) {
				tableModelElements.fireTableDataChanged();
			}

			@Override
			public void removedElementName(String elementName) {
				tableModelElements.fireTableDataChanged();
			}
		});
	}

	/**
	 * @return the markedElementsManager
	 */
	private MarkedElementsManager getMarkedElementsManager() {
		return markedElementsManager;
	}

	public void setElementSelectionManager(
			ElementSelectionManager elementSelectionManager) {
		this.elementSelectionManager = elementSelectionManager;
	}

	private ElementSelectionManager getElementSelectionManager() {
		return elementSelectionManager;
	}

}
