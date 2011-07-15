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

/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionExporter;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinderManager;
import cern.accsoft.steering.jmad.util.FileUtil;
import cern.accsoft.steering.jmad.util.StreamUtil;
import cern.accsoft.steering.jmad.util.xml.PersistenceServiceException;

/**
 * This is the default implementation of the {@link JMadModelDefinitionExporter}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadModelDefinitionExporterImpl implements JMadModelDefinitionExporter {
    /**
     * The persistence service to use to write model definition to xml. (injected by spring)
     */
    private ModelDefinitionPersistenceService persistenceService;

    /**
     * The class which keeps track of file finders for the model definitions.
     */
    private ModelFileFinderManager fileFinderManager;

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(JMadModelDefinitionExporterImpl.class);

    @Override
    public File export(JMadModelDefinition modelDefinition, File exportPath) {
        if (modelDefinition == null) {
            LOGGER.error("No model definition given to export.");
            return null;
        }
        if (exportPath == null) {
            LOGGER.error("No file given. Cannot export model definition.");
            return null;
        }

        if (ModelDefinitionUtil.isXmlFileName(exportPath.getName()) || exportPath.isDirectory()) {
            return exportAsFiles(modelDefinition, exportPath);
        } else {
            /* per default we export as zip */
            return exportAsZip(modelDefinition, exportPath);
        }
    }

    @Override
    public File exportAsFiles(JMadModelDefinition modelDefinition, File exportPath) {
        if (modelDefinition == null) {
            LOGGER.error("No model definition given to export.");
            return null;
        }
        if (exportPath == null) {
            LOGGER.error("No destination dir given. Cannot export model definition.");
            return null;
        }

        File xmlFile;
        File destDir;
        if (exportPath.isDirectory()) {
            destDir = exportPath;
            /* first save the definition file */
            xmlFile = new File(destDir.getAbsolutePath() + File.separator + getXmlFileName(modelDefinition));
        } else {
            destDir = exportPath.getAbsoluteFile().getParentFile();
            xmlFile = exportPath;
        }
        FileUtil.createDir(destDir, false);

        try {
            File modelDefFile = getPersistenceService().save(modelDefinition, xmlFile);

            /*
             * then we loop through all model files and copy all the files.
             */
            ModelFileFinder fileFinder = getFileFinderManager().getModelFileFinder(modelDefinition);
            for (ModelFile modelFile : getRequiredFiles(modelDefinition)) {
                /*
                 * We use the archive path here. So the file structure is the same as inside the zip archive.
                 */
                String archivePath = fileFinder.getArchivePath(modelFile);
                File file = new File(destDir.getAbsolutePath() + File.separator + archivePath);

                /*
                 * ensure that the parent dir exists
                 */
                FileUtil.createDir(file.getAbsoluteFile().getParentFile(), false);

                InputStream inStream = fileFinder.getStream(modelFile);
                if (!StreamUtil.toFile(inStream, file)) {
                    LOGGER.error("Could not write file '" + file.getAbsolutePath() + "'");
                    return null;
                }
            }
            return modelDefFile;
        } catch (PersistenceServiceException e) {
            LOGGER.error("Could not save model definition to file '" + xmlFile.getAbsolutePath() + "'.");
        }
        return null;
    }

    @Override
    public File exportAsZip(JMadModelDefinition modelDefinition, File file) {
        if (modelDefinition == null) {
            LOGGER.error("No model definition given to export.");
            return null;
        }
        if (file == null) {
            LOGGER.error("No file given. Cannot export model definition.");
            return null;
        }

        File zipFile = ModelDefinitionUtil.ensureZipFileExtension(file);

        try {
            /* Create the output stream */
            ZipOutputStream outStream;
            outStream = new ZipOutputStream(new FileOutputStream(zipFile));

            /* Add a zip entry to the output stream with the xml file as entry */
            outStream.putNextEntry(new ZipEntry(getXmlFileName(modelDefinition)));
            getPersistenceService().save(modelDefinition, outStream);
            outStream.closeEntry();

            /*
             * next we need the corresponding ModelFileFinder to find all the required files and put them in the
             * archive.
             */
            ModelFileFinder fileFinder = getFileFinderManager().getModelFileFinder(modelDefinition);

            /*
             * now we are ready to copy all the files into the archive.
             */
            for (ModelFile modelFile : getRequiredFiles(modelDefinition)) {
                outStream.putNextEntry(new ZipEntry(fileFinder.getArchivePath(modelFile)));
                InputStream inStream = fileFinder.getStream(modelFile);
                StreamUtil.copy(inStream, outStream);
                outStream.closeEntry();
                inStream.close();
            }

            outStream.close();
            return zipFile;
        } catch (IOException e) {
            LOGGER.error("Could not save model definition to zip file '" + zipFile.getAbsolutePath() + "'", e);
        } catch (PersistenceServiceException e) {
            LOGGER.error("Could not save model definition to zip file '" + zipFile.getAbsolutePath() + "'", e);
        }
        return null;
    }

    private String getXmlFileName(JMadModelDefinition modelDefinition) {
        if ((modelDefinition.getSourceInformation() != null)
                && (modelDefinition.getSourceInformation().getXmlFileName() != null)) {
            return modelDefinition.getSourceInformation().getXmlFileName();
        }
        return ModelDefinitionUtil.getProposedXmlFileName(modelDefinition);
    }

    /**
     * collects all the required files for a model definition. it returns a collection which will contain all the model
     * files with the same archive path only once.
     * 
     * @param modelDefinition the model definition for which to collect the files
     * @return all the files, with unique archive-path
     */
    private Collection<ModelFile> getRequiredFiles(JMadModelDefinition modelDefinition) {
        ModelFileFinder fileFinder = getFileFinderManager().getModelFileFinder(modelDefinition);
        Map<String, ModelFile> modelFiles = new HashMap<String, ModelFile>();
        for (ModelFile modelFile : ModelDefinitionUtil.getRequiredModelFiles(modelDefinition)) {
            String archivePath = fileFinder.getArchivePath(modelFile);
            modelFiles.put(archivePath, modelFile);
        }
        return modelFiles.values();
    }

    public void setPersistenceService(ModelDefinitionPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    private ModelDefinitionPersistenceService getPersistenceService() {
        return persistenceService;
    }

    public void setFileFinderManager(ModelFileFinderManager fileFinderManager) {
        this.fileFinderManager = fileFinderManager;
    }

    private ModelFileFinderManager getFileFinderManager() {
        return fileFinderManager;
    }

}
