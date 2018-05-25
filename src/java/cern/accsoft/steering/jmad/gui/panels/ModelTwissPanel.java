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

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.domain.twiss.TwissListener;
import cern.accsoft.steering.jmad.model.AbstractJMadModelListener;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelListener;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.util.gui.table.BeanTableEditHandler;
import cern.accsoft.steering.util.gui.table.BeanTableModel;

/**
 * this panel provides a table, in which one can edit the twiss parameters.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ModelTwissPanel extends JPanel implements EditHandlerUser<BeanTableEditHandler> {
    private static final long serialVersionUID = 7182385328020991187L;

    /** The model manager, which provides the actual model */
    private JMadModelManager modelManager;

    /** The table model which will contain the twiss-parameters */
    private BeanTableModel doubleTableModel = new BeanTableModel(TwissInitialConditions.class);

    /** The table model for boolean values */
    private BeanTableModel booleanTableModel = new BeanTableModel(TwissInitialConditions.class);

    /** The model which we display */
    private JMadModel model;

    /**
     * the listener, which is attached to all the models.
     */
    private JMadModelListener modelListener = new AbstractJMadModelListener() {

        @Override
        public void rangeChanged(Range newRange) {
            refreshTwiss();
        }

    };

    private TwissListener twissListener = new TwissListener() {

        @Override
        public void changedTwiss(TwissInitialConditions twiss) {
            doubleTableModel.fireTableDataChanged();
            booleanTableModel.fireTableDataChanged();
        }
    };

    /**
     * the default constructor
     */
    public ModelTwissPanel() {
        initComponents();
    }

    /**
     * create all the components
     */
    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 4;
        constraints.fill = GridBagConstraints.BOTH;

        JTable table;

        this.doubleTableModel.setEditable(true);
        table = new JTable(this.doubleTableModel);
        add(createScrollPane(table), constraints);

        constraints.gridy++;
        constraints.weighty = 1;
        this.booleanTableModel.setEditable(true);
        this.booleanTableModel.setValueClasses(new Class<?>[] { Boolean.class, boolean.class });
        table = new JTable(this.booleanTableModel);
        add(createScrollPane(table), constraints);
    }

    /**
     * creates a scrollPane containing the given component
     * 
     * @param component the component, which shall be included in the scrollPane
     * @return the scrollPane
     */
    private JScrollPane createScrollPane(Component component) {
        return new JScrollPane(component, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * sets the active model and registers the listener if required
     * 
     * @param model the model to set
     */
    private void setModel(JMadModel model) {
        if (model == null) {
            return;
        }
        this.model = model;
        model.addListener(modelListener);
        refreshTwiss();
    }

    private void refreshTwiss() {
        TwissInitialConditions twiss = this.model.getTwissInitialConditions();
        twiss.addListener(twissListener);
        this.doubleTableModel.setBean(twiss);
        this.doubleTableModel.fireTableDataChanged();
        this.booleanTableModel.setBean(twiss);
        this.booleanTableModel.fireTableDataChanged();
    }

    /**
     * @param modelManager the modelManager to set
     */
    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;

        setModel(this.modelManager.getActiveModel());

        this.modelManager.addListener(new JMadModelManagerAdapter() {

            @Override
            public void changedActiveModel(JMadModel newModel) {
                setModel(newModel);
            }
        });
    }

    /**
     * set an (optional) edit handler to the double-table model. This can be used to select certain properties of the
     * twiss, to allow to e.g. use them as variation parameters.
     * 
     * @param editHandler the editHandler to set
     */
    @Override
    public void setEditHandler(TablePanelEditHandler editHandler) {
        if (editHandler instanceof BeanTableEditHandler) {
            this.doubleTableModel.setEditHandler((BeanTableEditHandler) editHandler);
        }
    }
}
