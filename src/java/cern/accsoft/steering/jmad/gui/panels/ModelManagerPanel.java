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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import cern.accsoft.commons.util.Assert;
import cern.accsoft.steering.jmad.gui.manage.ChooseActionFactory;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerListener;
import cern.accsoft.steering.util.gui.CompUtils;

/**
 * This is a panel which displays all the currently open models in the ModelManager.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ModelManagerPanel extends JPanel {
    private static final long serialVersionUID = -244842549979376608L;

    /** The model manager, which knows about all active models */
    private JMadModelManager modelManager;

    /** The table model to provide all the information on the models */
    private ModelManagerTableModel tableModel;

    /** The factory that has the actions for the buttons */
    private ChooseActionFactory chooseActionFactory;

    /** The table for the models */
    private JTable table;

    private ComparisonPanel comparisonPanel;

    private JMadModelManagerListener modelManagerListener = new JMadModelManagerListener() {

        @Override
        public void removedModel(JMadModel removedModel) {
            tableModel.fireTableDataChanged();
        }

        @Override
        public void changedActiveModel(JMadModel newActiveModel) {
            setSelectedModel(newActiveModel);
        }

        @Override
        public void addedModel(JMadModel newModel) {
            tableModel.fireTableDataChanged();
        }
    };

    /**
     * init method called by spring
     */
    public void init() {
        Assert.argNotNull(comparisonPanel, "comparisonPanel");
        initComponents();
    }

    /**
     * creates all the swing components
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        this.tableModel = new ModelManagerTableModel();

        this.table = new JTable(this.tableModel);
        setSelectedModel(getModelManager().getActiveModel());

        this.table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                int modelIndex = table.convertRowIndexToModel(table.getSelectedRow());
                if (modelIndex < 0) {
                    return;
                }
                final JMadModel selectedModel = tableModel.getModel(modelIndex);

                if (e.getButton() == (MouseEvent.BUTTON3)) {

                    JMenuItem switchToActive = new JMenuItem("Set as active model");
                    switchToActive.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            setAsActiveModel(selectedModel);
                        }
                    });

                    JMenuItem compareToActive = new JMenuItem("Compare to the active model");
                    compareToActive.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JMadModel activeModel = modelManager.getActiveModel();
                            comparisonPanel.compareTwoModels(activeModel, selectedModel);
                            JFrame frame = new JFrame("Comparison for models");
                            frame.add(comparisonPanel);
                            frame.setSize(800, 600);
                            frame.setVisible(true);
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }
                    });

                    JPopupMenu menu = new JPopupMenu();
                    menu.setVisible(true);
                    menu.add(switchToActive);
                    menu.add(compareToActive);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }

                if (e.getButton() == (MouseEvent.BUTTON1) && e.getClickCount() > 1) {
                    setAsActiveModel(selectedModel);
                }
            }

            protected void setAsActiveModel(final JMadModel selectedModel) {
                if ((selectedModel != null) && (!selectedModel.equals(getModelManager().getActiveModel()))) {
                    getModelManager().setActiveModel(selectedModel);
                }
            }
        });

        getModelManager().addListener(modelManagerListener);
        this.add(CompUtils.createScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;

        JButton btn = new JButton(getChooseActionFactory().getNewModelAction());
        btn.setText(null);
        buttonPanel.add(btn, constraints);

        constraints.gridx++;
        btn = new JButton(getChooseActionFactory().getCloseActiveModelAction());
        btn.setText(null);
        buttonPanel.add(btn, constraints);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * sets the selection in the table to the given model
     * 
     * @param model the model to select
     */
    private void setSelectedModel(JMadModel model) {
        if (model == null) {
            return;
        }
        Integer index = getTableModelIndex(model);
        if (index != null) {
            int tableIndex = table.convertRowIndexToView(index);
            this.table.setRowSelectionInterval(tableIndex, tableIndex);
        }
    }

    /**
     * searches in the models of the modelmanager for the given model. If it finds the model, then it returns the index
     * within the list. Otherwise it returns <code>null</code>.
     * 
     * @param model the model to search
     * @return the index, or <code>null</code> if the model cannot be found.
     */
    private Integer getTableModelIndex(JMadModel model) {
        if (model == null) {
            return null;
        }

        int count = 0;
        for (JMadModel jmadModel : getModelManager().getModels()) {
            if (model.equals(jmadModel)) {
                return count;
            }
            count++;
        }

        /*
         * model not found
         */
        return null;
    }

    /**
     * a table model, which displays all available models
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private class ModelManagerTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        /** number of columns */
        private static final int COL_COUNT = 1;

        /** the index for the name column */
        private static final int COL_IDX_NAME = 0;

        @Override
        public int getColumnCount() {
            return COL_COUNT;
        }

        @Override
        public int getRowCount() {
            return getModelManager().getModels().size();
        }

        @Override
        public Object getValueAt(int row, int col) {
            JMadModel model = getModel(row);
            if (col == COL_IDX_NAME) {
                return model.getName();
            }
            return null;
        }

        @Override
        public Class<?> getColumnClass(int col) {
            if (col == COL_IDX_NAME) {
                return String.class;
            }
            return super.getColumnClass(col);
        }

        @Override
        public String getColumnName(int col) {
            if (col == COL_IDX_NAME) {
                return "name";
            }
            return super.getColumnName(col);
        }

        private JMadModel getModel(int row) {
            return getModelManager().getModels().get(row);
        }
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
    }

    private JMadModelManager getModelManager() {
        return modelManager;
    }

    public void setChooseActionFactory(ChooseActionFactory chooseActionFactory) {
        this.chooseActionFactory = chooseActionFactory;
    }

    private ChooseActionFactory getChooseActionFactory() {
        return chooseActionFactory;
    }

    public void setComparisonPanel(ComparisonPanel comparisonPanel) {
        this.comparisonPanel = comparisonPanel;
    }

}
