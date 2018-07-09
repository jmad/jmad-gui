/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.gui.actions;

import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import cern.accsoft.steering.jmad.gui.actions.event.SaveTwissEvent;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.OpticUtil;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerListener;
import org.springframework.context.event.EventListener;

/**
 * An action which shows a dialog to select a file and then saves the twiss of the actual model to the selected file.
 * 
 * @author kfuchsbe
 */
public class SaveTwissEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveTwissEventListener.class);

    private JMadGuiActions jmadGuiActions;
    private JMadModelManager modelManager;
    private JMadModelManagerListener modelManagerListener = new JMadModelManagerAdapter() {
        @Override
        public void changedActiveModel(JMadModel model) {
            updateEnabled(model);
        }
    };

    /**
     * Has to be called as soon as all the collaborators are injected. It checks that they are correctly set and
     * configures listeners to them as needed.
     * 
     * @throws IllegalStateException if one of the collaborators is not correctly set
     */
    public void init() {
        Preconditions.checkState(modelManager != null,
                "The model manager must not be null. Most probably a configuration error.");
        modelManager.addListener(modelManagerListener);
        updateEnabled(modelManager.getActiveModel());
    }

    @EventListener(SaveTwissEvent.class)
    public void saveTwissEventListener() {
        SwingUtilities.invokeLater(this::saveTwiss);
    }

    private void saveTwiss() {
        JMadModel activeModel = modelManager.getActiveModel();
        checkNonNullActiveModel(activeModel);
        Optional<File> file = askUserForTargetFile();
        if (file.isPresent()) {
            saveActualTwissToFile(activeModel, file.get());
        } else {
            LOGGER.info("No file selected by user. Nothing saved.");
        }
    }

    private Optional<File> askUserForTargetFile() {
        JFileChooser fileChooser = createFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (JFileChooser.APPROVE_OPTION == returnValue) {
            return Optional.of(fileChooser.getSelectedFile());
        } else {
            return Optional.empty();
        }
    }

    private JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return "Madx Twiss files";
            }

            @Override
            public boolean accept(File file) {
                return file.getName()
                        .endsWith(".tfs");
            }
        });
        return fileChooser;
    }

    private void saveActualTwissToFile(JMadModel model, File file) {
        try {
            model.twissToFile(OpticUtil.fullOpticsRequest(), file);
        } catch (JMadModelException e) {
            LOGGER.error("Could not write twiss of model '" + model + "'to file '" + file + "'", e);
        }
    }

    private void checkNonNullActiveModel(JMadModel activeModel) {
        if (activeModel == null) {
            throw new IllegalStateException("There is no active model. No TFS can be exported.");
        }
    }

    private void updateEnabled(JMadModel model) {
        /* It does not look very good to do it here.. */
        jmadGuiActions.getSaveTwissAction().setEnabled(model != null);
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
    }

    public void setJmadGuiActions(JMadGuiActions jmadGuiActions) {
        this.jmadGuiActions = jmadGuiActions;
    }
}
