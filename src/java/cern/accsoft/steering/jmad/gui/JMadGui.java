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

package cern.accsoft.steering.jmad.gui;

import cern.accsoft.gui.beans.AboutBox;
import cern.accsoft.gui.frame.Task;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.gui.actions.event.ChooseOpticsEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ChooseRangeEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CloseActiveModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExitEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExportModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ImportModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.NewModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ShowAboutBoxEvent;
import cern.accsoft.steering.jmad.gui.dialog.JMadOptionPane;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.impl.JMadGuiPreferencesImpl;
import cern.accsoft.steering.jmad.gui.panels.ModelOpticsSelectionPanel;
import cern.accsoft.steering.jmad.gui.panels.RangeSelectionPanel;
import cern.accsoft.steering.jmad.model.AbstractJMadModelListener;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.util.gui.DefaultAccsoftGui;
import cern.accsoft.steering.util.gui.SwingUserInteractor;
import cern.accsoft.steering.util.gui.UserInteractor;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * this class represents the main frame for the jmad-gui standalone application
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadGui extends DefaultAccsoftGui {
    private static final long serialVersionUID = -8292677890152652172L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAccsoftGui.class);
    private final static String TITLE_BASE = "jmad ";

    private final JMadGuiPreferences jmadGuiPreferences = new JMadGuiPreferencesImpl();
    private JMadService jMadService;
    private JMadModelSelectionDialogFactory jMadModelSelectionDialogFactory;
    private JMadModelManager modelManager;
    private UserInteractor userInteractor;
    private RangeSelectionPanel rangeSelectionPanel;
    private ModelOpticsSelectionPanel modelOpticsSelectionPanel;

    @Override
    protected WindowListener getWindowListener() {
        return new WindowAdapter() {
            public void windowClosing(@SuppressWarnings("unused") WindowEvent e) {
                if (jmadGuiPreferences.isCleanupOnClose()) {
                    cleanup();
                }
                if (jmadGuiPreferences.isExitOnClose()) {
                    System.exit(0);
                }
            }
        };
    }

    @Override
    protected void callbackBeforeInit() {
        setMainFrame(jmadGuiPreferences.isMainFrame());
    }

    @Override
    protected void callbackAfterInit() {
        jmadGuiPreferences.exitOnCloseProperty().addListener((obs, oldVal, close) -> setFrameCloseOperation(close));
        setFrameCloseOperation(jmadGuiPreferences.isExitOnClose());
    }

    protected void cleanup() {
        if (this.modelManager != null) {
            JMadModel model = this.modelManager.getActiveModel();
            if (model != null) {
                try {
                    model.cleanup();
                } catch (JMadModelException e) {
                    LOGGER.error("Error while cleaning up model", e);
                }
            }
        }
    }

    protected void updateTitleAccordingTo(JMadModel model) {
        if (model != null) {
            model.addListener(new AbstractJMadModelListener() {
                @Override
                public void opticsDefinitionChanged() {
                    updateTitle();
                }

                @Override
                public void rangeChanged(@SuppressWarnings("unused") Range newRange) {
                    updateTitle();
                }
            });
        }
        updateTitle();
    }

    protected void updateTitle() {
        if (this.modelManager == null) {
            return;
        }

        JMadModel model = this.modelManager.getActiveModel();

        if (model == null) {
            setTitle(TITLE_BASE);
        } else {
            setTitle(TITLE_BASE + model.getDescription());
        }
    }

    @Override
    protected ImageIcon getImageIcon() {
        return Icon.JMAD.getImageIcon();
    }

    public JMadModel showCreateModelDialog() {
        return JMadOptionPane.showCreateModelDialog(jMadModelSelectionDialogFactory, jMadService);
    }

    public void showExportModelDefinitionDialog() {
        JMadOptionPane.showExportModelDefinitionDialog(getJFrame(), jMadService);
    }

    public JMadModel showImportModelDefinitionDialog() {
        return JMadOptionPane.showImportModelDefinitionDialog(getJFrame(), jMadService);
    }

    public void showRangeDefinitionChooseDialog() {
        userInteractor.showPanelDialog(rangeSelectionPanel, getJFrame());
    }

    public void showOpticsDefinitionChooseDialog() {
        userInteractor.showPanelDialog(modelOpticsSelectionPanel, getJFrame());
    }

    public void showCreateNewModelDialog() {
        final JMadModel model = showCreateModelDialog();
        if (model != null) {
            Task task = new Task() {
                @Override
                protected Object construct() {
                    LOGGER.info("Starting Initialization of model '" + model.getName() + "'");
                    try {
                        model.reset();
                    } catch (JMadModelException e) {
                        LOGGER.error("Error while initializing Model '" + model.getName() + "'.", e);
                        return null;
                    }
                    LOGGER.info("Initialization of model '" + model.getName() + "' finished.");
                    return model;
                }

            };
            task.setName("Initializing model '" + model.getName() + "'");
            task.setCancellable(false);
            task.start();
        }
    }

    @EventListener(NewModelEvent.class)
    public void createNewModelEventListener() {
        SwingUtilities.invokeLater(this::showCreateNewModelDialog);
    }

    @EventListener(ImportModelEvent.class)
    public void importModelEventListener() {
        SwingUtilities.invokeLater(this::showImportModelDefinitionDialog);
    }

    @EventListener(ExportModelEvent.class)
    public void exportModelEventListener() {
        SwingUtilities.invokeLater(this::showExportModelDefinitionDialog);
    }

    @EventListener(CloseActiveModelEvent.class)
    public void closeActiveModelEventListener() {
        SwingUtilities.invokeLater(this::closeActiveModel);
    }

    @EventListener(ExitEvent.class)
    public void exitEventListener() {
        SwingUtilities.invokeLater(this::exitJMad);
    }

    @EventListener(ChooseRangeEvent.class)
    public void chooseRangeEventListener() {
        SwingUtilities.invokeLater(this::showRangeDefinitionChooseDialog);
    }

    @EventListener(ChooseOpticsEvent.class)
    public void chooseOpticsEventListener() {
        SwingUtilities.invokeLater(this::showOpticsDefinitionChooseDialog);
    }

    @EventListener(ShowAboutBoxEvent.class)
    public void showAboutInfoEventListener() {
        SwingUtilities.invokeLater(this::showAboutBox);
    }

    private void setFrameCloseOperation(boolean close) {
        getJFrame().setDefaultCloseOperation(close ? WindowConstants.EXIT_ON_CLOSE : WindowConstants.HIDE_ON_CLOSE);
    }

    private void exitJMad() {
        modelManager.cleanup();
        System.exit(0);
    }

    private void closeActiveModel() {
        JMadModel model = modelManager.getActiveModel();
        if (model == null) {
            return;
        }
        try {
            model.cleanup();
        } catch (JMadModelException e) {
            LOGGER.error("Error while cleaning up model.", e);
        }
        modelManager.removeModel(model);
    }

    private void showAboutBox() {
        AboutBox aboutBox = new AboutBox(getJFrame());
        aboutBox.setIcon(Icon.SPLASH.getImageIcon());
        aboutBox.setText("JMad GUI", "cern-accsoft-steering-jmad-gui",
                "(C) Copyright CERN 2008-2010  Kajetan Fuchsberger AB-OP-SPS", null);
        aboutBox.setVisible(true);
    }

    public void setRangeSelectionPanel(RangeSelectionPanel rangeSelectionPanel) {
        this.rangeSelectionPanel = rangeSelectionPanel;
    }

    public void setModelOpticsSelectionPanel(ModelOpticsSelectionPanel modelOpticsSelectionPanel) {
        this.modelOpticsSelectionPanel = modelOpticsSelectionPanel;
    }

    public void setUserInteractor(SwingUserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    public void setJMadService(JMadService jMadService) {
        this.jMadService = jMadService;
    }

    public void setJMadModelSelectionDialogFactory(JMadModelSelectionDialogFactory jMadModelSelectionDialogFactory) {
        this.jMadModelSelectionDialogFactory = jMadModelSelectionDialogFactory;
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;

        this.modelManager.addListener(new JMadModelManagerAdapter() {
            @Override
            public void changedActiveModel(JMadModel newModel) {
                updateTitleAccordingTo(newModel);
            }
        });

        if (modelManager.getActiveModel() != null) {
            updateTitleAccordingTo(modelManager.getActiveModel());
        }
    }

    public JMadGuiPreferences getJmadGuiPreferences() {
        return jmadGuiPreferences;
    }

}
