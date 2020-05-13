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

import static java.util.Objects.requireNonNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.Range;
import cern.accsoft.steering.jmad.gui.actions.event.ChooseOpticsEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ChooseRangeEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CloseActiveModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CreateModelFromFileEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CreateModelFromRepositoryEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CreateModelFromUriEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExitEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExportModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExportModelUriEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ShowAboutBoxEvent;
import cern.accsoft.steering.jmad.gui.dialog.AboutDialog;
import cern.accsoft.steering.jmad.gui.dialog.JMadOptionPane;
import cern.accsoft.steering.jmad.gui.executor.AsyncExecutor;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.impl.JMadGuiPreferencesImpl;
import cern.accsoft.steering.jmad.gui.panels.GuiLogPanel;
import cern.accsoft.steering.jmad.gui.panels.ModelOpticsSelectionPanel;
import cern.accsoft.steering.jmad.gui.panels.RangeSelectionPanel;
import cern.accsoft.steering.jmad.model.AbstractJMadModelListener;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.util.gui.UserInteractor;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogFactory;
import org.jmad.modelpack.service.JMadModelPackageService;
import org.jmad.modelpack.util.ModelUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

/**
 * this class represents the main frame for the jmad-gui standalone application
 *
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadGui extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(JMadGui.class);
    private static final String TITLE_BASE = "JMad ";
    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 768;

    private final JMadGuiPreferences jmadGuiPreferences = new JMadGuiPreferencesImpl();
    private AsyncExecutor asyncExecutor;
    private JMadService jMadService;
    private JMadModelSelectionDialogFactory jMadModelSelectionDialogFactory;
    private JMadModelPackageService jMadModelPackageService;
    private JMadModelManager modelManager;
    private UserInteractor userInteractor;
    private RangeSelectionPanel rangeSelectionPanel;
    private ModelOpticsSelectionPanel modelOpticsSelectionPanel;
    private JPanel mainPanel;
    private JMenuBar jmadMenuBar;
    private JToolBar toolBar;
    private GuiLogPanel guiLogPanel;

    public final void init() {
        requireNonNull(mainPanel, "mainPanel cannot be null. Configuration problem");
        setupFramePreferences();

        setLayout(new BorderLayout());
        setIconImage(Icon.JMAD.getImageIcon().getImage());
        setContentPane(mainPanel);

        if (jmadMenuBar != null) {
            setJMenuBar(jmadMenuBar);
        }
        if (toolBar != null) {
            add(toolBar, BorderLayout.PAGE_START);
        }

        if (guiLogPanel != null) {
            add(guiLogPanel, BorderLayout.PAGE_END);
        }

        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    @Override
    public void validate() {
        super.validate();
        setPreferredSize(getSize());
    }

    public void showGui() {
        SwingUtilities.invokeLater(() -> {
            pack();
            setVisible(true);
        });
    }

    private void setupFramePreferences() {
        jmadGuiPreferences.exitOnCloseProperty().addListener((obs, oldVal, close) -> setFrameCloseOperation(close));
        setFrameCloseOperation(jmadGuiPreferences.isExitOnClose());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(@SuppressWarnings("unused") WindowEvent e) {
                if (jmadGuiPreferences.isCleanupOnClose()) {
                    cleanup();
                }
                if (jmadGuiPreferences.isExitOnClose()) {
                    System.exit(0);
                }
            }
        });
    }

    private void cleanup() {
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

    private void updateTitleAccordingTo(JMadModel model) {
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

    private void updateTitle() {
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

    private void showExportModelDefinitionDialog() {
        JMadModel model = modelManager.getActiveModel();
        if (model == null) {
            LOGGER.warn("No active model to export!");
            return;
        }
        JMadOptionPane.showExportModelDefinitionDialog(this, model.getModelDefinition(), jMadService);
    }

    private void showExportModelUriDialog() {
        JMadModel model = modelManager.getActiveModel();
        if (model == null) {
            LOGGER.warn("No active model to export!");
            return;
        }
        try {
            String modelUri = ModelUris.modelUri(model).toASCIIString();
            LOGGER.info("Model URI: {}", modelUri);
            JOptionPane.showMessageDialog(this, new JTextField(modelUri));
        } catch (Exception e) {
            LOGGER.error("Error getting model URI: {}", e.getMessage(), e);
        }
    }

    private void showRangeDefinitionChooseDialog() {
        userInteractor.showPanelDialog(rangeSelectionPanel, this);
    }

    private void showOpticsDefinitionChooseDialog() {
        userInteractor.showPanelDialog(modelOpticsSelectionPanel, this);
    }

    private void showCreateModelFromRepositoryDialog() {
        initializeModel(JMadOptionPane.showCreateModelDialog(jMadModelSelectionDialogFactory, jMadService));
    }

    private void showCreateModelFromFileDialog() {
        initializeModel(JMadOptionPane.showCreateModelFromFileDialog(this, jMadService));
    }

    private void showCreateModelFromUriDialog() {
        initializeModel(JMadOptionPane.showCreateModelFromUriDialog(this, jMadModelPackageService));
    }

    private void initializeModel(JMadModel model) {
        if (model == null) {
            return;
        }
        asyncExecutor.submitAsync("Initializing model '" + model.getName() + "'", () -> {
            LOGGER.info("Starting Initialization of model '{}'", model.getName());
            try {
                model.reset();
                LOGGER.info("Initialization of model '{}' finished.", model.getName());
            } catch (JMadModelException e) {
                LOGGER.error("Error while initializing Model '{}'.", model.getName(), e);
            }
        });
    }

    @EventListener(CreateModelFromRepositoryEvent.class)
    public void createModelFromRepositoryEventListener() {
        SwingUtilities.invokeLater(this::showCreateModelFromRepositoryDialog);
    }

    @EventListener(CreateModelFromUriEvent.class)
    public void createModelFromUriEventListener() {
        SwingUtilities.invokeLater(this::showCreateModelFromUriDialog);
    }

    @EventListener(CreateModelFromFileEvent.class)
    public void createModelFromFileEventListener() {
        SwingUtilities.invokeLater(this::showCreateModelFromFileDialog);
    }

    @EventListener(ExportModelEvent.class)
    public void exportModelEventListener() {
        SwingUtilities.invokeLater(this::showExportModelDefinitionDialog);
    }

    @EventListener(ExportModelUriEvent.class)
    public void exportModelUriEventListener() {
        SwingUtilities.invokeLater(this::showExportModelUriDialog);
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
        setDefaultCloseOperation(close ? WindowConstants.EXIT_ON_CLOSE : WindowConstants.HIDE_ON_CLOSE);
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
        AboutDialog aboutDialog = new AboutDialog(this);
        aboutDialog.setIcon(Icon.SPLASH.getImageIcon());
        aboutDialog.setText("JMad GUI", "cern-accsoft-steering-jmad-gui",
                "(C) Copyright CERN 2008-2020  Kajetan Fuchsberger<br>and the BE-OP-LHC software team.");
        aboutDialog.show();
    }

    public void setRangeSelectionPanel(RangeSelectionPanel rangeSelectionPanel) {
        this.rangeSelectionPanel = rangeSelectionPanel;
    }

    public void setModelOpticsSelectionPanel(ModelOpticsSelectionPanel modelOpticsSelectionPanel) {
        this.modelOpticsSelectionPanel = modelOpticsSelectionPanel;
    }

    public void setUserInteractor(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    public void setJMadService(JMadService jMadService) {
        this.jMadService = jMadService;
    }

    public void setJMadModelSelectionDialogFactory(JMadModelSelectionDialogFactory jMadModelSelectionDialogFactory) {
        this.jMadModelSelectionDialogFactory = jMadModelSelectionDialogFactory;
    }

    public void setjMadModelPackageService(JMadModelPackageService jMadModelPackageService) {
        this.jMadModelPackageService = jMadModelPackageService;
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

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void setJMadMenuBar(JMenuBar menuBar) {
        this.jmadMenuBar = menuBar;
    }

    public void setJMadToolBar(JToolBar toolBar) {
        this.toolBar = toolBar;
    }

    public void setGuiLogPanel(GuiLogPanel guiLogPanel) {
        this.guiLogPanel = guiLogPanel;
    }

    public void setAsyncExecutor(AsyncExecutor asyncExecutor) {
        this.asyncExecutor = asyncExecutor;
    }
}
