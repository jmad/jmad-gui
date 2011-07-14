// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
package cern.accsoft.steering.jmad.modeldefs.domain;

import java.io.File;

/**
 * This interface describes the source of a model definition. This can be e.g. JAR, ZIP or LOCAL
 * 
 * @author kfuchsbe
 */
public interface SourceInformation {

    /**
     * depending on {@link #getSourceType()} this either represents the name of zip archive or the base directory if
     * local (i.e. the path where the .jmd.xml file resides.)
     * 
     * @return the path
     */
    public File getRootPath();

    /**
     * if the model definition was loaded from an xml file (or a zip archive) then this returns the name of the xml file
     * (or the name of the zip entry)
     * 
     * @return the name of the xml file from which the model definition was loaded.
     */
    public String getXmlFileName();

    /**
     * @return The {@link SourceType} which indicates from where the model definition was loaded.
     */
    public SourceType getSourceType();

    /**
     * this enum describes the type of the source of the model definition. Depending on this value the other data in the
     * {@link SourceInformation} is interpreted differently. This also influences the search behaviour of a
     * {@link cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder}.
     * 
     * @author kfuchsbe
     */
    public static enum SourceType {
        /**
         * JAR means that the model definition was loaded from a jar archive or the source tree if run from the
         * development environment. All {@link cern.accsoft.steering.jmad.domain.file.ModelFile}s will be searched
         * relative to the {@link cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil#BASE_CLASS} with the
         * path offset {@link cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil#PACKAGE_OFFSET}.
         */
        JAR,

        /**
         * ZIP indicates that the model definition was loaded from a zip archive. In this case the
         * {@link SourceInformation#getRootPath()} returns the zip archive. All
         * {@link cern.accsoft.steering.jmad.domain.file.ModelFile}s will be searched within this zip archive.
         */
        ZIP,

        /**
         * FILE means that the model definition was loaded from a simple file. In this case
         * {@link SourceInformation#getRootPath()} returns the directory where the xml-file is located. All
         * {@link cern.accsoft.steering.jmad.domain.file.ModelFile}s will be searched relative to this path.
         */
        FILE;
    }
}
