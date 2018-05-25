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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelStartupConfiguration;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.modeldefs.JMadModelDefinitionManager;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.util.gui.panels.Applyable;
import cern.accsoft.steering.util.gui.panels.Titleable;

public class ModelDefinitionSelectionPanel extends JPanel implements Titleable, Applyable {
    private final static Logger logger = Logger.getLogger(ModelDefinitionSelectionPanel.class);

    private static final long serialVersionUID = -3095464304653777882L;

    /** the jmad-service to create models */
    private JMadService jmadService;

    private JComboBox cboModel;

    private JMadModelStartupConfiguration jmadModelStartupConfiguration = null;

    private ActionListener modelSelectionComboListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            updateSubPanels();
        }
    };

    /**
     * An (optional) range selection panel. If this is present, then also the sequence and range can be selected.
     */
    private RangeSelectionPanel rangeSelectionPanel;

    public ModelDefinitionSelectionPanel() {
        /* nothing to do */
    }

    private void updateSubPanels() {
        if (ModelDefinitionSelectionPanel.this.rangeSelectionPanel != null) {
            JMadModelDefinition modelDefinition = getActiveModelDefinition();
            if (modelDefinition != null) {
                ModelDefinitionSelectionPanel.this.rangeSelectionPanel.setActiveModelDefinition(modelDefinition,
                        modelDefinition.getDefaultSequenceDefinition().getDefaultRangeDefinition());
            }
        }
    }

    public ModelDefinitionSelectionPanel(boolean showRangeSelection) {
        if (showRangeSelection) {
            this.rangeSelectionPanel = new RangeSelectionPanel();
            this.rangeSelectionPanel.init();
        }
    }

    public void init() {
        initComponenets();
    }

    private void initComponenets() {
        setLayout(new BorderLayout());

        /*
         * Combo-Boxes
         */
        JPanel comboPanel = new JPanel(new GridBagLayout());
        add(comboPanel, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;

        comboPanel.add(new JLabel("Model: "), constraints);

        constraints.gridx++;
        cboModel = createModelSelectionComboBox();
        comboPanel.add(cboModel, constraints);

        if (this.rangeSelectionPanel != null) {
            add(this.rangeSelectionPanel, BorderLayout.SOUTH);
        }

        updateSubPanels();

        /*
         * if a model manager is already available then we try to set the model.
         */
        if (getModelManager() != null) {
            JMadModel model = getModelManager().getActiveModel();
            if (model != null) {
                setActiveModel(model);
            }
        }
    }

    /**
     * sets the values of all comboboxes to the values given by the model.
     * 
     * @param model
     */
    private void setActiveModel(JMadModel model) {
        if (model == null) {
            return;
        }
        setActiveModelDefinition(model.getModelDefinition());
        if (this.rangeSelectionPanel != null) {
            this.rangeSelectionPanel.setActiveModel(model);
        }
    }

    /**
     * sets the active model and initializes the Combo-boxes accordingly.
     * 
     * @param model
     */
    public void setActiveModelDefinition(JMadModelDefinition modelDefinition) {
        cboModel.setSelectedItem(modelDefinition);
    }

    /**
     * @return the actually selected {@link JMadModelDefinition}
     */
    public JMadModelDefinition getActiveModelDefinition() {
        return (JMadModelDefinition) this.cboModel.getSelectedItem();
    }

    private JComboBox createModelSelectionComboBox() {
        JComboBox comboBox = new JComboBox(getModelDefinitionManager().getAllModelDefinitions().toArray());
        comboBox.setToolTipText("Selects the actual model.");
        comboBox.addActionListener(this.modelSelectionComboListener);
        return comboBox;
    }

    @Override
    public String getTitle() {
        return "Select model";
    }

    @Override
    public boolean apply() {
        RangeDefinition rangeDefinition = null;
        this.jmadModelStartupConfiguration = null;
        if (this.rangeSelectionPanel != null) {
            rangeDefinition = this.rangeSelectionPanel.getActiveRangeDefinition();
            if (rangeDefinition != null) {
                JMadModelStartupConfiguration startupConfiguration = new JMadModelStartupConfiguration();
                startupConfiguration.setInitialRangeDefinition(rangeDefinition);
                startupConfiguration.setLoadDefaultRange(false);
                this.jmadModelStartupConfiguration = startupConfiguration;
            }
        }
        return getActiveModelDefinition() != null;
    }

    @Override
    public void cancel() {
        /* do nothing */
    }

    public void setJmadService(JMadService jmadService) {
        this.jmadService = jmadService;
        this.jmadService.getModelManager().addListener(new JMadModelManagerAdapter() {
            @Override
            public void changedActiveModel(JMadModel model) {
                setActiveModel(model);
            }
        });
    }

    private JMadService getJmadService() {
        if (this.jmadService == null) {
            logger.warn("jmadService not set. Maybe config error.");
        }
        return jmadService;
    }

    private JMadModelManager getModelManager() {
        if (getJmadService() == null) {
            return null;
        }
        return getJmadService().getModelManager();
    }

    private JMadModelDefinitionManager getModelDefinitionManager() {
        return getJmadService().getModelDefinitionManager();
    }

    public JMadModelStartupConfiguration getStartupConfiguration() {
        return jmadModelStartupConfiguration;
    }
}
