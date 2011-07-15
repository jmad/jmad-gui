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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.SourceInformationImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.SourceInformation.SourceType;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionImporter;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.xml.PersistenceServiceException;

/**
 * The default implementation of a {@link JMadModelDefinitionImporter}
 * 
 * @author kfuchsbe
 */
public class JMadModelDefinitionImporterImpl implements JMadModelDefinitionImporter {

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(JMadModelDefinitionImporterImpl.class);

    /** The persistence service used to read model definitions from file */
    private ModelDefinitionPersistenceService persistenceService;

    @Override
    public JMadModelDefinition importModelDefinition(File file) {
        Collection<JMadModelDefinition> modelDefinitions = importModelDefinitions(file);
        if (modelDefinitions.size() > 1) {
            LOGGER.warn("There are more than one model definitions in the file '" + file.getAbsolutePath()
                    + "'. Returning only the first one..");
        }
        if (!modelDefinitions.isEmpty()) {
            return modelDefinitions.iterator().next();
        }
        return null;
    }

    @Override
    public Collection<JMadModelDefinition> importModelDefinitions(File file) {
        String fileName = file.getName();
        if (file.isDirectory()) {
            return importFromDir(file);
        } else if (ModelDefinitionUtil.isXmlFileName(fileName)) {
            return importFromXml(file);
        } else if (ModelDefinitionUtil.isZipFileName(fileName)) {
            return importFromZip(file);
        } else {
            LOGGER.warn("File '" + file.getAbsolutePath()
                    + "' seems to be neither a model definition xml nor a zip. Do not know how to import!");
            return new ArrayList<JMadModelDefinition>();
        }
    }

    private Collection<JMadModelDefinition> importFromDir(File dir) {
        List<JMadModelDefinition> definitions = new ArrayList<JMadModelDefinition>();

        /*
         * collect all the model definition files
         */
        File[] fileList = dir.listFiles();
        for (File file : fileList) {
            if (ModelDefinitionUtil.isXmlFileName(file.getName())) {
                definitions.addAll(importFromXml(file));
            }
        }
        return definitions;
    }

    /**
     * imports the model definitions from an xml file.
     * 
     * @param xmlFile the file from which to load the model definitions
     * @return all the model definitions contained in this xml file
     */
    private Collection<JMadModelDefinition> importFromXml(File xmlFile) {
        List<JMadModelDefinition> modelDefinitions = new ArrayList<JMadModelDefinition>();
        JMadModelDefinition modelDefinition = null;
        try {
            modelDefinition = getPersistenceService().load(xmlFile);
        } catch (PersistenceServiceException e) {
            LOGGER.error("could not load model definition from xml file '" + xmlFile.getAbsolutePath() + "'.", e);
        }
        if (modelDefinition instanceof JMadModelDefinitionImpl) {
            ((JMadModelDefinitionImpl) modelDefinition).setSourceInformation(new SourceInformationImpl(SourceType.FILE,
                    xmlFile.getAbsoluteFile().getParentFile(), xmlFile.getName()));
        }
        if (modelDefinition != null) {
            modelDefinitions.add(modelDefinition);
        }
        return modelDefinitions;
    }

    /**
     * imports all model definitions from the given zip file.
     * 
     * @param file the zipFile from which to import the model definitions
     * @return the model definitions
     */
    private Collection<JMadModelDefinition> importFromZip(File file) {
        List<JMadModelDefinition> modelDefinitions = new ArrayList<JMadModelDefinition>();

        ZipFile zipFile;
        try {
            zipFile = new ZipFile(file);
        } catch (ZipException e) {
            LOGGER.error("Could not open ZipFile '" + file.getAbsolutePath() + "'", e);
            return modelDefinitions;
        } catch (IOException e) {
            LOGGER.error("Could not open ZipFile '" + file.getAbsolutePath() + "'", e);
            return modelDefinitions;
        }

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (ModelDefinitionUtil.isXmlFileName(entry.getName())) {
                InputStream inputStream;
                try {
                    inputStream = zipFile.getInputStream(entry);
                } catch (IOException e1) {
                    LOGGER.error("Could not get inputstream for entry '" + entry.getName() + "'");
                    break;
                }
                JMadModelDefinition modelDefinition = null;
                try {
                    modelDefinition = getPersistenceService().load(inputStream);
                } catch (PersistenceServiceException e) {
                    LOGGER.error(
                            "could not load model definition '" + entry.getName() + "' from zip file '"
                                    + file.getAbsolutePath() + "'.", e);
                }
                if (modelDefinition instanceof JMadModelDefinitionImpl) {
                    ((JMadModelDefinitionImpl) modelDefinition).setSourceInformation(new SourceInformationImpl(
                            SourceType.ZIP, file, entry.getName()));
                }
                if (modelDefinition != null) {
                    modelDefinitions.add(modelDefinition);
                    LOGGER.info("Imported model definition '" + modelDefinition.getName() + "' from zip file '"
                            + file.getAbsolutePath() + "'.");
                }
            }
        }

        return modelDefinitions;
    }

    public void setPersistenceService(ModelDefinitionPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    private ModelDefinitionPersistenceService getPersistenceService() {
        return persistenceService;
    }

}
