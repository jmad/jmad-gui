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

import java.awt.Frame;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import javafx.application.Platform;

import cern.accsoft.steering.jmad.gui.panels.ModelDefinitionSelectionPanel;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelStartupConfiguration;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.util.gui.dialog.PanelDialog;
import javafx.scene.control.Dialog;
import org.jmad.modelpack.gui.conf.JMadModelSelectionDialogFactory;
import org.jmad.modelpack.gui.domain.JMadModelSelection;
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

	public final static JMadModel showCreateModelDialog(JMadModelSelectionDialogFactory modelpackDialogFactory, JMadService jmadService) {
		CountDownLatch latch = new CountDownLatch(1);
		AtomicReference<JMadModel> selectedModel = new AtomicReference<>();

        Dialog<JMadModelSelection> jMadModelSelectionDialog = modelpackDialogFactory.selectionDialog();

        Platform.runLater(() -> {
            Optional<JMadModelSelection> selection = jMadModelSelectionDialog.showAndWait();

			if (selection.isPresent()) {
                JMadModelDefinition modelDefinition = selection.get().modelDefinition();
                JMadModelStartupConfiguration startupConfiguration = selection.get().startupConfiguration();
                selectedModel.set(createModel(jmadService, modelDefinition, startupConfiguration));
            }

			latch.countDown();
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
		    throw new RuntimeException("Interrupted while waiting for user model selection");
		}
		return selectedModel.get();
	}

	private final static JMadModel createModel(JMadService jmadService,
			JMadModelDefinition modelDefinition,
			JMadModelStartupConfiguration startupConfiguration) {

		JMadModel model = jmadService.createModel(modelDefinition);
		if (startupConfiguration != null) {
			model.setStartupConfiguration(startupConfiguration);

		}
		// model.reset();
		if (jmadService.getModelManager() != null) {
			jmadService.getModelManager().setActiveModel(model);
		}
		return model;

	}

	public final static void showExportModelDefinitionDialog(Frame frame,
			JMadService jmadService) {
		ModelDefinitionSelectionPanel modelSelectionPanel = new ModelDefinitionSelectionPanel(
				false);
		modelSelectionPanel.setJmadService(jmadService);
		modelSelectionPanel.init();
		if (PanelDialog.show(modelSelectionPanel, frame)) {
			JMadModelDefinition modelDefinition = modelSelectionPanel
					.getActiveModelDefinition();
			int returnValue = FILECHOOSER.showSaveDialog(frame);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				jmadService.getModelDefinitionExporter().export(
						modelDefinition, FILECHOOSER.getSelectedFile());
			} else {
				LOGGER.debug("Definition export aborted by user.");
			}
		} else {
			LOGGER.debug("Definition export aborted by user.");
		}
	}

	public final static JMadModel showImportModelDefinitionDialog(Frame frame,
			JMadService jmadService) {
		int returnValue = FILECHOOSER.showOpenDialog(frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			JMadModelDefinition modelDefinition = jmadService
					.getModelDefinitionImporter().importModelDefinition(
							FILECHOOSER.getSelectedFile());
			return createModel(jmadService, modelDefinition, null);
		} else {
			LOGGER.debug("Definition import aborted by user.");
			return null;
		}
	}
}
