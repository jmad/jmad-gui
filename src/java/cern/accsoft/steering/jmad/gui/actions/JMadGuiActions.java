package cern.accsoft.steering.jmad.gui.actions;

import cern.accsoft.steering.jmad.gui.actions.event.ChooseOpticsEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ChooseRangeEvent;
import cern.accsoft.steering.jmad.gui.actions.event.CloseActiveModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExitEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ExportModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ImportModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.NewModelEvent;
import cern.accsoft.steering.jmad.gui.actions.event.SaveTwissEvent;
import cern.accsoft.steering.jmad.gui.actions.event.ShowAboutBoxEvent;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.util.gui.NamedAction;
import org.springframework.context.ApplicationEventPublisher;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;

import static cern.accsoft.steering.jmad.gui.icons.Icon.SAVE;

public final class JMadGuiActions {

    private final ApplicationEventPublisher publisher;

    private Action newModelAction = new NamedAction("New model", "Creates a new model from internal model definition.") {
        private static final long serialVersionUID = 2464183652035511611L;

        {
            putValue(SMALL_ICON, Icon.NEW.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new NewModelEvent());
        }

    };

    private Action importModelAction = new NamedAction("Import model definition",
            "Creates a new model from an file- model definition.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.IMPORT.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ImportModelEvent());
        }
    };

    private Action exportModelAction = new NamedAction("Export model definition",
            "Saves one of the internal model definitions to a file.") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.EXPORT.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ExportModelEvent());
        }
    };

    private Action closeActiveModelAction = new NamedAction("Close active model", "Closes the active model") {
        private static final long serialVersionUID = 1L;

        {
            putValue(SMALL_ICON, Icon.DELETE.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            publisher.publishEvent(new CloseActiveModelEvent());
        }
    };

    private Action exitAction = new AbstractAction("Exit", Icon.EXIT.getImageIcon()) {
        private static final long serialVersionUID = 1L;

        {
            putValue(Action.SHORT_DESCRIPTION, "Exit Application");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ExitEvent());
        }
    };

    private Action chooseRangeAction = new NamedAction("select range",
            "Select one of the possible ranges of the model.") {
        private static final long serialVersionUID = -2928278893227845086L;

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ChooseRangeEvent());
        }
    };

    private Action chooseOpticsAction = new NamedAction("select optics", "Select optics definition of the model.") {
        private static final long serialVersionUID = -9088381330307065526L;

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new ChooseOpticsEvent());
        }
    };

    private Action saveTwissAction = new NamedAction("Save Twiss", "Saves the actual twiss to a file.") {
        {
            putValue(SMALL_ICON, SAVE.getImageIcon());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            publisher.publishEvent(new SaveTwissEvent());
        }
    };

    private Action showAboutBoxAction = new AbstractAction("About") {
        private static final long serialVersionUID = -1695054883852564439L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            publisher.publishEvent(new ShowAboutBoxEvent());
        }
    };

    public JMadGuiActions(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public Action getNewModelAction() {
        return newModelAction;
    }

    public Action getImportModelAction() {
        return importModelAction;
    }

    public Action getExportModelAction() {
        return exportModelAction;
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
