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
