// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
package cern.accsoft.steering.jmad.ant;

import static cern.accmodel.commons.util.ResourceUtil.prependPathOffset;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.selectors.BaseSelector;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.modeldefs.ClassPathModelDefinitionFinder;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.modeldefs.io.impl.JMadModelDefinitionImporterImpl;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;
import cern.accsoft.steering.jmad.modeldefs.io.impl.XmlModelDefinitionPersistenceService;

/**
 * This class represents a ant-file selector task which determines from a model-definition which files are necessary
 * from the repository. XXX For the time being this is the simplest possible implementation: It just uses naively the
 * {@link ClassPathModelDefinitionFinder} and accepts all files which are used by one of the models.
 * 
 * @author kfuchsbe
 */
public class JMadModelFileSelector extends BaseSelector {

    /** The required file-names */
    private Set<String> fileNames = new HashSet<String>();

    /** The directory where to search for model definitions */
    private String defDir = null;

    private void init() {
        BasicConfigurator.configure();
        ModelDefinitionPersistenceService xmlModelDefinitionPersistenceService = new XmlModelDefinitionPersistenceService();
        JMadModelDefinitionImporterImpl importer = new JMadModelDefinitionImporterImpl();
        importer.setPersistenceService(xmlModelDefinitionPersistenceService);
        this.fileNames = getModelRepositoryFileNames(importer.importModelDefinitions(new File(defDir)));
        System.out.println(this.fileNames.size() + " required model files detected.");
    }

    @Override
    public boolean isSelected(File baseDir, String fileName, File file) throws BuildException {
        validate();

        /*
         * the file name should exactly be relative to the repository base-path. (except that only slashes are used)
         */
        String normalizedFileName = fileName.replaceAll("\\\\", "/");

        return this.fileNames.contains(normalizedFileName);
    }

    /**
     * walks through all the given model definitions and collects the (unique) file names of repository files. These
     * then should be relative paths to the repository - base path.
     * 
     * @param modelDefinitions the model definitions for which to find the files
     * @return a set of filenames
     */
    private Set<String> getModelRepositoryFileNames(Collection<JMadModelDefinition> modelDefinitions) {
        Set<String> foundFileNames = new HashSet<String>();
        for (JMadModelDefinition modelDefinition : modelDefinitions) {
            for (ModelFile modelFile : ModelDefinitionUtil.getRequiredModelFiles(modelDefinition)) {
                if (ModelFileLocation.REPOSITORY.equals(modelFile.getLocation())) {
                    /* 1) the name itself */
                    String resourcePath = modelFile.getName();
                    /* 2) the model definition - dependent offset */
                    resourcePath = prependPathOffset(resourcePath,
                            modelFile.getLocation().getPathOffset(modelDefinition.getModelPathOffsets()));

                    foundFileNames.add(resourcePath);
                }
            }
        }
        return foundFileNames;
    }

    @Override
    public void verifySettings() {
        super.verifySettings();
        if (this.defDir == null) {
            setError("defdir not set.");
        }
    }

    /**
     * Sets the directory in which to search for model definitions. After setting the directory, {@link #init()} is
     * called and the selector is ready to use.
     * 
     * @param defDir the directory to search for {@link JMadModelDefinition}s.
     */
    public void setDefDir(String defDir) {
        this.defDir = defDir;
        init();
    }

}
