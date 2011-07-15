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
package cern.accsoft.steering.jmad.modeldefs.io;

import java.io.File;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This is the interface of a class that can export jmad model-definitions to flat files or zip files.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface JMadModelDefinitionExporter {

    /**
     * exports the model definition to the given path. If the path is a directory then it is exported as separated local
     * files, If is a filename then it is exported as a jmd.zip file.
     * 
     * @param modelDefinition the model definition to export.
     * @param exportPath the destination path
     * @return either the xml file to which the model definition was written if the export was to separate files or the
     *         zip file to which the whole model definition and files were written.
     */
    public abstract File export(JMadModelDefinition modelDefinition, File exportPath);

    /**
     * exports the model definition to separate files within the destination directory
     * 
     * @param modelDefinition the modelDefinition to export
     * @param destDir the destination directory
     * @return the xml file to which the model definition was written to
     */
    public abstract File exportAsFiles(JMadModelDefinition modelDefinition, File destDir);

    /**
     * exports the model definition to a zip file containing all the required files.
     * 
     * @param modelDefinition the {@link JMadModelDefinition} to export
     * @param zipFile the zip file which shall finally contain the model definitioin
     * @return the zip file to which the data was written (can be different since the default extension might have been
     *         added)
     */
    public abstract File exportAsZip(JMadModelDefinition modelDefinition, File zipFile);
}
