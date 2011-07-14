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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.gui.components.DoubleTableCellRenderer;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.util.gui.menu.MousePopupListener;
import cern.accsoft.steering.util.gui.menu.TablePopupMenu;
import cern.accsoft.steering.util.gui.panels.Applyable;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;
import cern.accsoft.steering.util.gui.panels.Titleable;
import cern.accsoft.steering.util.gui.table.SelectionSetTableModel;
import cern.accsoft.steering.util.gui.table.TableModelSelectionAdapter;

/**
 * this class represents the panel, which allows to view all strengths of a model (that is the content of the
 * strength-file) and in addition it provides the possibility to select certain strengths (if a EditHandler is set).
 * 
 * @author kfuchsbe
 */
public class ModelOpticsSelectionPanel extends JPanel implements Titleable,
        Applyable {
    private static final long serialVersionUID = 1733380183463632497L;

    private static final Logger LOGGER = Logger
            .getLogger(ModelOpticsSelectionPanel.class);

    /** the table model for the display */
    private ModelOpticsDefinitionTableModel tableModel;

    /** the actual model */
    private JMadModel model;

    /** The object which keeps track of the selected row. */
    private TableModelSelectionAdapter selectionAdapter;

    /**
     * the constructor
     */
    public ModelOpticsSelectionPanel() {
        super(new BorderLayout());
    }

    public void init() {
        initComponents();

        if (this.model != null) {
            refreshDisplay();
        }
    }

    /**
     * set the model-manager which determines if e.g. the model changed.
     * 
     * @param modelManager the modelManager to set.
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
        tableModel = new ModelOpticsDefinitionTableModel();

        JTable table = new JTable(tableModel);
        table.setDefaultRenderer(Double.class, new DoubleTableCellRenderer());
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        new MousePopupListener(table, new TablePopupMenu(tableModel));
        selectionAdapter = new TableModelSelectionAdapter(table);
        tableModel.setTableModelSelectionAdapter(selectionAdapter);

        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(new TableFilterPanel(table), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    private void setSelectedOpticsDefinition() {
        int row = selectionAdapter.getSelectedRowIndex();
        OpticsDefinition opticsDefinition = getOpticsDefinitions().get(row);
        try {
            getModel().setActiveOpticsDefinition(opticsDefinition);
        } catch (JMadModelException e) {
            LOGGER.error("Could not set Optic [" + opticsDefinition.getName()
                    + "] in Model [" + getModel() + "].");
        }
    }

    /**
     * this inner class is the Table model for the displayed Table.
     * 
     * @author kfuchsbe
     */
    class ModelOpticsDefinitionTableModel extends SelectionSetTableModel {
        private final static int COLUMN_COUNT = 1;

        private final static int COL_NAME = 0;

        private static final long serialVersionUID = 1L;

        @Override
        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        @Override
        public int getRowCount() {
            List<OpticsDefinition> opticsDefinitions = getOpticsDefinitions();
            if (opticsDefinitions != null) {
                return opticsDefinitions.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public Object getValueAt(int row, int col) {
            List<OpticsDefinition> opticsDefinitions = getOpticsDefinitions();
            if (opticsDefinitions == null) {
                return null;
            }

            OpticsDefinition opticsDefinition = opticsDefinitions.get(row);
            switch (col) {
            case COL_NAME:
                return opticsDefinition.getName();
            default:
                return null;
            }
        }

        @Override
        public Class<?> getColumnClass(int col) {
            switch (col) {
            case COL_NAME:
                return String.class;
            default:
                return null;
            }
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
            case COL_NAME:
                return "optics name";
            default:
                return null;
            }
        }

    }

    private void setModel(JMadModel model) {
        this.model = model;
    }

    private List<OpticsDefinition> getOpticsDefinitions() {
        if (getModel() != null) {
            return getModel().getModelDefinition().getOpticsDefinitions();
        } else {
            return new ArrayList<OpticsDefinition>();
        }
    }

    private JMadModel getModel() {
        return model;
    }

    public SelectionSetTableModel getTableModel() {
        return this.tableModel;
    }

    @Override
    public String getTitle() {
        return "Optics selection";
    }

    @Override
    public boolean apply() {
        setSelectedOpticsDefinition();
        return true;
    }

    @Override
    public void cancel() {
        /* do nothing */
    }
}
