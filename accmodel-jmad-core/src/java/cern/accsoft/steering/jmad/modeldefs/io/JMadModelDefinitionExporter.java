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
/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io;

import java.io.File;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This is the interface of a class that can export jmad model-definitions to flat files or zip files.
 * 
 * @author kfuchsbe
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
