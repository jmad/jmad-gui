/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.gui.actions;

import static cern.accsoft.steering.jmad.gui.icons.Icon.SAVE;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.OpticUtil;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerListener;

/**
 * An action which shows a dialog to select a file and then saves the twiss of the actual model to the selected file.
 * 
 * @author kfuchsbe
 */
public class SaveTwissAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveTwissAction.class);

    private JMadModelManager modelManager;
    private JMadModelManagerListener modelManagerListener = new JMadModelManagerListener() {

        @Override
        public void removedModel(JMadModel model) {
            /* Nothing to do here */
        }

        @Override
        public void changedActiveModel(JMadModel model) {
            updateEnabled(model);
        }

        @Override
        public void addedModel(JMadModel model) {
            /* Nothing to do here */
        }
    };

    /**
     * Default constructor to create the action
     */
    public SaveTwissAction() {
        super("Save Twiss");
        putValue(SHORT_DESCRIPTION, "Saves the actual twiss to a file.");
        putValue(SMALL_ICON, SAVE.getImageIcon());
    }

    /**
     * Has to be called as soon as all the collaborators are injected. It checks that they are correctly set and
     * configures listeners to them as needed.
     * 
     * @throws if one of the collaborators is not correctly set
     */
    public void init() {
        Preconditions.checkState(modelManager != null,
                "The model manager must not be null. Most probably a configuration error.");
        modelManager.addListener(modelManagerListener);
        updateEnabled(modelManager.getActiveModel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
        setEnabled(model != null);
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
    }

}
