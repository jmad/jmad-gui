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

package cern.accsoft.steering.jmad.gui.manage.impl;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.gui.dialog.JMadOptionPane;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.ChooseActionFactory;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.ModelDefinitionChooser;
import cern.accsoft.steering.jmad.gui.panels.ModelOpticsSelectionPanel;
import cern.accsoft.steering.jmad.gui.panels.ModelDefinitionSelectionPanel;
import cern.accsoft.steering.jmad.gui.panels.RangeSelectionPanel;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.util.gui.NamedAction;
import cern.accsoft.steering.util.gui.UserInteractor;

/**
 * The swing-implementation of the {@link ModelDefinitionChooser}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class SwingModelDefinitionChooser implements ModelDefinitionChooser, ChooseActionFactory {

    /** The logger for the class */
    private final static Logger LOGGER = Logger.getLogger(SwingModelDefinitionChooser.class);

    /** The class to communicate with the user */
    private UserInteractor userInteractor;

    /** The model manager who knows about the models */
    private JMadModelManager modelManager;

    /** The jmad-service */
    private JMadService jmadService;

    /** The frame to use as parent for the dialog */
    private Frame frame;

    /** The panel to select the active range */
    private RangeSelectionPanel rangeSelectionPanel;

    /** The panel to select the optics definition */
    private ModelOpticsSelectionPanel modelOpticsSelectionPanel;

    /** The preferences to activate/deactivate the actions */
    private JMadGuiPreferences jmadGuiPreferences;

    //
    // actions
    //

    /** the action to choose a new model */
    private Action newModelAction = new NamedAction("New model", "Creates a new model from internal model definition.") {
        private static final long serialVersionUID = 2464183652035511611L;

        {
            putValue(SMALL_ICON, Icon.NEW.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showModelChooseDialog();
        }

    };

    /** The action to create a new model from a file-model definition */
    private Action importAction = new NamedAction("Import model definition",
            "Creates a new model from an file- model definition.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.IMPORT.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JMadOptionPane.showImportModelDefinitionDialog(frame, jmadService);
        }
    };

    /** The action to export an internal model definition to a file. */
    private Action exportAction = new NamedAction("Export model definition",
            "Saves one of the internal model definitions to a file.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.EXPORT.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JMadOptionPane.showExportModelDefinitionDialog(frame, jmadService);
        }
    };

    private Action closeActiveModelAction = new NamedAction("Close active model", "Closes the active model") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.DELETE.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            closeActiveModel();
        }
    };

    private Action exitAction = new AbstractAction("Exit", Icon.EXIT.getImageIcon()) {
        private static final long serialVersionUID = 1L;

        {
            putValue(Action.SHORT_DESCRIPTION, "Exit Application");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            getModelManager().cleanup();
            System.exit(0);
        }
    };

    /** the action to choose a newe RangeDefinition within the model. */
    private Action chooseRangeAction = new NamedAction("select range",
            "Select one of the possible ranges of the model.") {
        private static final long serialVersionUID = -2928278893227845086L;

        @Override
        public void actionPerformed(ActionEvent e) {
            showRangeDefinitionChooseDialog();
        }
    };

    /** the action to choose a new optics */
    private Action chooseOpticsAction = new NamedAction("select optics", "Select optics definition of the model.") {
        private static final long serialVersionUID = -9088381330307065526L;

        @Override
        public void actionPerformed(ActionEvent e) {
            showOpticsDefinitionChooseDialog();
        }
    };

    //
    // methods
    //

    /**
     * init method called by spring
     */
    public void init() {
        newModelAction.setEnabled(getJmadGuiPreferences().isEnabledChangeModel());
        closeActiveModelAction.setEnabled(getJmadGuiPreferences().isEnabledChangeModel());
        chooseRangeAction.setEnabled(getJmadGuiPreferences().isEnabledChangeRange());
        chooseOpticsAction.setEnabled(getJmadGuiPreferences().isEnabledChangeOptic());
        exitAction.setEnabled(getJmadGuiPreferences().isExitOnClose());
    }

    @Override
    public void showModelChooseDialog() {
        JMadOptionPane.showCreateModelDialog(frame, jmadService);
    }

    /**
     * cleans up the active model and removes it from the modelManager
     */
    private void closeActiveModel() {
        JMadModel model = getModelManager().getActiveModel();
        if (model == null) {
            return;
        }
        try {
            model.cleanup();
        } catch (JMadModelException e) {
            LOGGER.error("Error while cleaning up model.", e);
        }
        getModelManager().removeModel(model);
    }

    @Override
    public void showRangeDefinitionChooseDialog() {
        userInteractor.showPanelDialog(getRangeSelectionPanel());
    }

    @Override
    public void showOpticsDefinitionChooseDialog() {
        userInteractor.showPanelDialog(getModelOpticsSelectionPanel());
    }

    //
    // Getters and setters
    //

    public void setUserInteractor(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    public UserInteractor getUserInteractor() {
        return userInteractor;
    }

    public void setRangeSelectionPanel(RangeSelectionPanel rangeSelectionPanel) {
        this.rangeSelectionPanel = rangeSelectionPanel;
    }

    private RangeSelectionPanel getRangeSelectionPanel() {
        return rangeSelectionPanel;
    }

    public void setModelOpticsSelectionPanel(ModelOpticsSelectionPanel modelOpticsSelectionPanel) {
        this.modelOpticsSelectionPanel = modelOpticsSelectionPanel;
    }

    private ModelOpticsSelectionPanel getModelOpticsSelectionPanel() {
        return modelOpticsSelectionPanel;
    }

    //
    // Methods of interface ChooseActionFactory
    //

    @Override
    public Action getNewModelAction() {
        return this.newModelAction;
    }

    @Override
    public Action getChooseOpticsAction() {
        return this.chooseOpticsAction;
    }

    @Override
    public Action getChooseRangeAction() {
        return this.chooseRangeAction;
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
    }

    private JMadModelManager getModelManager() {
        return modelManager;
    }

    @Override
    public Action getCloseActiveModelAction() {
        return closeActiveModelAction;
    }

    @Override
    public Action getImportAction() {
        return this.importAction;
    }

    @Override
    public Action getExportAction() {
        return this.exportAction;
    }

    public void setJmadGuiPreferences(JMadGuiPreferences jmadGuiPreferences) {
        this.jmadGuiPreferences = jmadGuiPreferences;
    }

    private JMadGuiPreferences getJmadGuiPreferences() {
        return jmadGuiPreferences;
    }

    @Override
    public Action getExitAction() {
        return this.exitAction;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setJmadService(JMadService jmadService) {
        this.jmadService = jmadService;
    }

    public JMadService getJmadService() {
        return jmadService;
    }

}
