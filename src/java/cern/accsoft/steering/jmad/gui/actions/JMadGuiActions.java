package cern.accsoft.steering.jmad.gui.actions;

import static cern.accsoft.steering.jmad.gui.icons.Icon.SAVE;
import static javax.swing.SwingUtilities.invokeLater;

import javax.swing.*;
import java.awt.event.ActionEvent;

import cern.accsoft.steering.jmad.gui.actions.event.ChooseOpticsEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ChooseRangeEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CloseActiveModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CreateModelFromFileEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CreateModelFromRepositoryEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CreateModelFromUriEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExitEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExportModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExportModelUriEvent;
import cern.accsoft.steering.jmad.gui.actions.event.SaveTwissEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ShowAboutBoxEvent;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.JMadGuiPreferences;
import cern.accsoft.steering.util.gui.NamedAction;
import org.springframework.context.ApplicationEventPublisher;

public final class JMadGuiActions {

    private final ApplicationEventPublisher publisher;

    private final Action createModelAction = new NamedAction("Create Model ...", "Creates a new model ...") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.NEW.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JComponent) {
                JComponent sourceComponent = (JComponent) source;
                JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.add(createModelFromRepositoryAction);
                popupMenu.add(createModelFromUriAction);
                popupMenu.add(createModelFromFileAction);
                popupMenu.show(sourceComponent, 0, 0);
            }
        }

    };

    private final Action createModelFromRepositoryAction = new NamedAction("Create Model from Repository",
            "Creates a new model from the repository.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.OPEN_REPO.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new CreateModelFromRepositoryEvent());
        }

    };

    private final Action createModelFromFileAction = new NamedAction("Create Model from ZIP File",
            "Creates a new model from an local ZIP file.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.OPEN_FILE.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new CreateModelFromFileEvent());
        }
    };

    private final Action createModelFromUriAction = new NamedAction("Create Model from URI",
            "Creates a new model from a URI.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.OPEN_URI.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new CreateModelFromUriEvent());
        }
    };

    private final Action exportModelAction = new NamedAction("Export model definition",
            "Saves the active model definition to a file.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.EXPORT.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ExportModelEvent());
        }
    };

    private final Action exportModelUriAction = new NamedAction("Get the active model URI",
            "Gets an URI of the active model.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.EXPORT_URI.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ExportModelUriEvent());
        }
    };

    private final Action closeActiveModelAction = new NamedAction("Close active model", "Closes the active model") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.DELETE.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            publisher.publishEvent(new CloseActiveModelEvent());
        }
    };

    private final Action exitAction = new AbstractAction("Exit", Icon.EXIT.getImageIcon()) {
        private static final long serialVersionUID = 1L;

        {
            putValue(Action.SHORT_DESCRIPTION, "Exit Application");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ExitEvent());
        }
    };

    private final Action chooseRangeAction = new NamedAction("select range",
            "Select one of the possible ranges of the model.") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ChooseRangeEvent());
        }
    };

    private final Action chooseOpticsAction = new NamedAction("select optics",
            "Select optics definition of the model.") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ChooseOpticsEvent());
        }
    };

    private final Action saveTwissAction = new NamedAction("Save Twiss", "Saves the actual twiss to a file.") {
        {
            putValue(SMALL_ICON, SAVE.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new SaveTwissEvent());
        }
    };

    private final Action showAboutBoxAction = new AbstractAction("About") {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            publisher.publishEvent(new ShowAboutBoxEvent());
        }
    };

    public JMadGuiActions(ApplicationEventPublisher publisher, JMadGuiPreferences guiPreferences) {
        this.publisher = publisher;
        guiPreferences.enabledChangeModelProperty().addListener((obs, old, enable) -> invokeLater(() -> {
            createModelAction.setEnabled(enable);
            createModelFromFileAction.setEnabled(enable);
            createModelFromRepositoryAction.setEnabled(enable);
            createModelFromUriAction.setEnabled(enable);
            closeActiveModelAction.setEnabled(enable);
        }));
        guiPreferences.enabledChangeOpticProperty().addListener((obs, old, enable) -> invokeLater(() -> //
                chooseOpticsAction.setEnabled(enable)));
        guiPreferences.enabledChangeRangeProperty().addListener((obs, old, enable) -> invokeLater(() -> //

                chooseRangeAction.setEnabled(enable)));
    }

    public Action getCreateModelAction() {
        return createModelAction;
    }

    public Action getCreateModelFromRepositoryAction() {
        return createModelFromRepositoryAction;
    }

    public Action getCreateModelFromUriAction() {
        return createModelFromUriAction;
    }

    public Action getCreateModelFromFileAction() {
        return createModelFromFileAction;
    }

    public Action getExportModelAction() {
        return exportModelAction;
    }

    public Action getExportModelUriAction() {
        return exportModelUriAction;
    }

    public Action getCloseActiveModelAction() {
        return closeActiveModelAction;
    }

    public Action getExitAction() {
        return exitAction;
    }

    public Action getChooseRangeAction() {
        return chooseRangeAction;
    }

    public Action getChooseOpticsAction() {
        return chooseOpticsAction;
    }

    public Action getSaveTwissAction() {
        return saveTwissAction;
    }

    public Action getShowAboutBoxAction() {
        return showAboutBoxAction;
    }
}
