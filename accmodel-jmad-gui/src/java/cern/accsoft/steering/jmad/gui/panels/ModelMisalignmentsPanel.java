// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.domain.machine.RangeListener;
import cern.accsoft.steering.jmad.domain.misalign.Misalignment;
import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;
import cern.accsoft.steering.jmad.gui.components.DoubleTableCellRenderer;
import cern.accsoft.steering.jmad.model.AbstractJMadModelListener;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelListener;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.util.gui.CompUtils;
import cern.accsoft.steering.util.gui.panels.TableFilterPanel;
import cern.accsoft.steering.util.gui.table.BeanTableEditHandler;
import cern.accsoft.steering.util.gui.table.BeanTableModel;
import cern.accsoft.steering.util.gui.table.SelectionSetTableModel;
import cern.accsoft.steering.util.gui.table.TableModelSelectionAdapter;

/**
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ModelMisalignmentsPanel extends JPanel {
    private static final long serialVersionUID = -2761017481167940042L;

    /** the logger for the class */
    private final static Logger logger = Logger.getLogger(ModelMisalignmentsPanel.class);

    /**
     * The initial location of the devider between elements-table and element-table.
     */
    private final static int DIVIDER_LOCATION = 150;

    /** the modelManager, which returns the active model */
    private JMadModelManager modelManager;

    /** the table model for all misalignment-configurations */
    private MisalignmentsTableModel misalignmentsTableModel;

    /** the tableModel for one misalignment */
    private BeanTableModel misalignmentTableModel;

    /** the text field which displays the name of the actual selected element. */
    private JTextField txtElementName = null;

    /** the listener, which shall be added to every model */
    private JMadModelListener modelListener = new AbstractJMadModelListener() {

        @Override
        public void rangeChanged(Range newRange) {
            newRange.addListener(new RangeListener() {

                @Override
                public void addedMisalignments(MisalignmentConfiguration misalignmentConfiguration) {
                    /* XXX never called */
                    misalignmentsTableModel.fireTableDataChanged();
                    validate();
                }

                @Override
                public void addedMisalignments(List<MisalignmentConfiguration> arg0) {
                    misalignmentsTableModel.fireTableDataChanged();
                    validate();
                }
            });

            misalignmentsTableModel.fireTableDataChanged();
            validate();
        }

        @Override
        public void becameDirty() {
            /* for the moment do not update every time a single value changes. */
        }
    };

    /**
     * init-method
     */
    public void init() {
        initComponents();
    }

    /**
     * create all containing Components
     */
    private final void initComponents() {
        setLayout(new BorderLayout());

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

        misalignmentsTableModel = new MisalignmentsTableModel();
        table = new JTable(misalignmentsTableModel);
        table.setDefaultRenderer(Double.class, new DoubleTableCellRenderer());
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(new MisalignmentsSelectionAdapter(table));
        table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        misalignmentsTableModel.setTableModelSelectionAdapter(new TableModelSelectionAdapter(table));
        JScrollPane elementsScrollPane = CompUtils.createScrollPane(table);

        /*
         * the sorter for the elements-table
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

        misalignmentTableModel = new BeanTableModel(Misalignment.class);
        misalignmentTableModel.setEditable(true);
        table = new JTable(misalignmentTableModel);

        detailPanel.add(CompUtils.createScrollPane(table), constraints);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        splitPane.setLeftComponent(listPanel);
        // splitPane.setLeftComponent(elementsScrollPane);
        splitPane.setRightComponent(detailPanel);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setDividerLocation(DIVIDER_LOCATION);

    }

    /**
     * the table model for the table of all misalignments
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private class MisalignmentsTableModel extends SelectionSetTableModel {
        private static final long serialVersionUID = -5686224769800416136L;
        private final static int COL_COUNT = 1;
        private final static int COL_INDEX_NAME = 0;

        @Override
        public int getColumnCount() {
            return COL_COUNT;
        }

        @Override
        public int getRowCount() {
            return getMisalignmentConfigurations().size();
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (COL_INDEX_NAME == col) {
                return getMisalignmentConfigurations().get(row).getElementName();
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public Class<?> getColumnClass(int col) {
            switch (col) {
            case COL_INDEX_NAME:
                return String.class;
            default:
                return null;
            }
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
            case COL_INDEX_NAME:
                return "element name";
            default:
                return null;
            }
        }
    }

    /**
     * This class is the implementation of a listener to change the misalignment
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private class MisalignmentsSelectionAdapter implements ListSelectionListener {
        private JTable table = null;

        public MisalignmentsSelectionAdapter(JTable table) {
            this.table = table;
        }

        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getSource() == table.getSelectionModel()) {
                int index = table.getSelectedRow();

                if (index >= 0) {
                    setCurrentMisalignment(table.convertRowIndexToModel(index));
                }
            }
        }
    }

    /**
     * sets the the element of the given index as the active one.
     * 
     * @param index
     */
    private void setCurrentMisalignment(int index) {
        if ((index >= 0) && (modelManager != null)) {
            if (index < getMisalignmentConfigurations().size()) {
                MisalignmentConfiguration misalignmentConfiguration = getMisalignmentConfigurations().get(index);
                txtElementName.setText(misalignmentConfiguration.getElementName());
                misalignmentTableModel.setBean(misalignmentConfiguration.getMisalignment());
            }
        }
    }

    /**
     * @return the active Misalignment-configurations to display
     */
    private List<MisalignmentConfiguration> getMisalignmentConfigurations() {
        if ((getModelManager() == null) || (getModelManager().getActiveModel() == null)
                || (getModelManager().getActiveModel().getActiveRange() == null)) {
            return new ArrayList<MisalignmentConfiguration>();
        }
        return getModelManager().getActiveModel().getActiveRange().getMisalignmentConfigurations();
    }

    /**
     * @return all mislignment-beans currently selected in the table
     */
    private List<Misalignment> getSelectedMisalignments() {
        List<Misalignment> misalignments = new ArrayList<Misalignment>();
        List<MisalignmentConfiguration> configurations = getMisalignmentConfigurations();
        List<Integer> indizes = misalignmentsTableModel.getTableModelSelectionAdapter().getSelectedRowIndizes();
        for (Integer index : indizes) {
            misalignments.add(configurations.get(index).getMisalignment());
        }
        return misalignments;
    }

    /**
     * @param modelManager the modelManager to set
     */
    public void setModelManager(JMadModelManager modelManager) {
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
                misalignmentsTableModel.fireTableDataChanged();
            }

        });
    }

    /**
     * @return the modelManager
     */
    private JMadModelManager getModelManager() {
        if (this.modelManager == null) {
            logger.warn("model manager not set. Maybe config error.");
        }
        return modelManager;
    }

    /**
     * set an edit-handler for the bean-properties of misalignments.
     * 
     * @param editHandler the editHandler to set
     */
    public void setEditHandler(BeanTableEditHandler editHandler) {
        this.misalignmentTableModel.setEditHandler(new EditHandlerAdapter(editHandler));
    }

    /**
     * this class adapts the general {@link ModelElementsPanelEditHandler} so, that we can cope with the
     * multiple-selection in the misalignments-table.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private class EditHandlerAdapter implements BeanTableEditHandler {

        /** the parent, which to delegate most things to */
        private BeanTableEditHandler parent;

        /**
         * the constructor, which needs a parent to delegate most calls to.
         * 
         * @param parent
         */
        private EditHandlerAdapter(BeanTableEditHandler parent) {
            this.parent = parent;
        }

        @Override
        public Boolean getCheckValue(Object bean, String propertyName) {
            return parent.getCheckValue(bean, propertyName);
        }

        @Override
        public String getCheckableColumnHeader() {
            return parent.getCheckableColumnHeader();
        }

        @Override
        public void setCheckValue(Object bean, String propertyName, boolean value) {
            /*
             * this is the only special thing: we ignore the bean-paramater. Instead we apply to all actually selected
             * beans.
             */
            List<Misalignment> misalignments = getSelectedMisalignments();
            for (Misalignment misalignment : misalignments) {
                parent.setCheckValue(misalignment, propertyName, value);
            }
        }

        @Override
        public boolean isEditable() {
            /*
             * TODO: change to true, but how to handle for many models?
             */
            return false;
        }

    }
}
