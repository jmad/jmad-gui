// @formatter:off
/*******************************************************************************
 * This file is part of JMad. Copyright (c) 2008-2011, CERN. All rights reserved. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.gui.dialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.Optional;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelStartupConfiguration;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;
import cern.accsoft.steering.jmad.service.JMadService;
import com.google.common.base.Strings;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogFactory;
import org.jmad.modelpack.gui.domain.JMadModelSelection;
import org.jmad.modelpack.gui.domain.JMadModelSelectionType;
import org.jmad.modelpack.service.JMadModelPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a collection of utility methods to show dialogs related to
 * jmad-models
 *
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadOptionPane {

    private final static Logger LOGGER = LoggerFactory.getLogger(JMadOptionPane.class);

    private final static JFileChooser FILECHOOSER = new JFileChooser();

    static {
        FILECHOOSER.addChoosableFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return "JMad Model Definition XML files";
            }

            @Override
            public boolean accept(File f) {
                return ModelDefinitionUtil.isXmlFileName(f.getName());
            }
        });
        FILECHOOSER.addChoosableFileFilter(new FileFilter() {

            @Override
            public String getDescription() {
                return "JMad Model Definition ZIP files";
            }

            @Override
            public boolean accept(File f) {
                return ModelDefinitionUtil.isZipFileName(f.getName());
            }
        });
        FILECHOOSER.setAcceptAllFileFilterUsed(true);
    }

    private JMadOptionPane() {
        /* no instantiation */
    }

    public static JMadModel showCreateModelDialog(JMadModelSelectionDialogFactory modelpackDialogFactory,
            JMadService jmadService) {
        Optional<JMadModelSelection> selection = modelpackDialogFactory
                .showAndWaitModelSelection(JMadModelSelectionType.ALL);

        if (selection.isPresent()) {
            JMadModelDefinition modelDefinition = selection.get().modelDefinition();
            JMadModelStartupConfiguration startupConfiguration = selection.get().startupConfiguration().orElse(null);
            return jmadService.createModel(modelDefinition, startupConfiguration);
        }

        return null;
    }

    public static void showExportModelDefinitionDialog(Frame frame, JMadModelDefinition modelDefinition,
            JMadService jmadService) {
        FILECHOOSER.setDialogTitle("Exporting model " + modelDefinition.getName());
        int returnValue = FILECHOOSER.showSaveDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            jmadService.getModelDefinitionExporter().export(modelDefinition, FILECHOOSER.getSelectedFile());
        } else {
            LOGGER.debug("Definition export aborted by user.");
        }
    }

    public static JMadModel showCreateModelFromFileDialog(Frame frame, JMadService jmadService) {
        FILECHOOSER.setDialogTitle("Select model definition file ...");
        int returnValue = FILECHOOSER.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            JMadModelDefinition modelDefinition = jmadService.getModelDefinitionImporter()
                    .importModelDefinition(FILECHOOSER.getSelectedFile());
            return jmadService.createModel(modelDefinition);
        } else {
            LOGGER.debug("Definition import aborted by user.");
            return null;
        }
    }

    public static JMadModel showCreateModelFromUriDialog(Frame frame, JMadModelPackageService modelPackageService) {
        String userUri = JOptionPane.showInputDialog(frame, "Enter URI to open ...");
        if (Strings.isNullOrEmpty(userUri)) {
            LOGGER.debug("URI import aborted by user.");
            return null;
        }
        try {
            URI uri = new URI(userUri);
            return modelPackageService.createModelFromUri(uri).block();
        } catch (Exception e) {
            LOGGER.error("Error opening '{}': {}", userUri, e.getMessage(), e);
            return null;
        }
    }
}
