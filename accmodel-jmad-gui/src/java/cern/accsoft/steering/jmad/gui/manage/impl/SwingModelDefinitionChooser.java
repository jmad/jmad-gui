/**
 * 
 */
package cern.accsoft.steering.jmad.gui.manage.impl;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.ChooseActionFactory;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.jmad.gui.manage.ModelDefinitionChooser;
import cern.accsoft.steering.jmad.gui.panels.ModelOpticsSelectionPanel;
import cern.accsoft.steering.jmad.gui.panels.ModelSelectionPanel;
import cern.accsoft.steering.jmad.gui.panels.RangeSelectionPanel;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.util.gui.NamedAction;
import cern.accsoft.steering.util.gui.UserInteractor;

/**
 * The swing-implementation of the {@link ModelDefinitionChooser}
 * 
 * @author kfuchsbe
 */
public class SwingModelDefinitionChooser implements ModelDefinitionChooser, ChooseActionFactory {

    /** The logger for the class */
    private final static Logger LOGGER = Logger.getLogger(SwingModelDefinitionChooser.class);

    /** The class to communicate with the user */
    private UserInteractor userInteractor;

    /** The model manager who knows about the models */
    private JMadModelManager modelManager;

    /** The panel to select a new model. */
    private ModelSelectionPanel modelSelectionPanel;

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
        ModelSelectionPanel panel = getModelSelectionPanel();
        panel.setInitModel(true);
        userInteractor.showPanelDialog(panel);
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

    public void setModelSelectionPanel(ModelSelectionPanel modelSelectionPanel) {
        this.modelSelectionPanel = modelSelectionPanel;
    }

    private ModelSelectionPanel getModelSelectionPanel() {
        return modelSelectionPanel;
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

}
