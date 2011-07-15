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
package cern.accsoft.steering.jmad.modeldefs.domain;

import java.io.File;

/**
 * The default implementation for the {@link SourceInformation} interface.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class SourceInformationImpl implements SourceInformation {

    /** The path to the jmd.xml or the name of the zip archive */
    private final File rootPath;

    /** The type of the model definition (like JAR, ZIP or LOCAL) */
    private final SourceType sourceType;

    /** The name of the xml file from which the model definition was loaded. */
    private final String xmlFileName;

    /**
     * The constructor which requires both the {@link SourceInformation.SourceType} and the rootPath
     * 
     * @param sourceType defines the method how the model-file paths will be treated.
     * @param rootPath the path to the zip or base dir for the model definitions
     * @param xmlFileName the name of the file from which the modelDefinition was loaded
     */
    public SourceInformationImpl(SourceType sourceType, File rootPath, String xmlFileName) {
        this.sourceType = sourceType;
        this.rootPath = rootPath;
        this.xmlFileName = xmlFileName;
    }

    @Override
    public File getRootPath() {
        return this.rootPath;
    }

    @Override
    public SourceType getSourceType() {
        return this.sourceType;
    }

    @Override
    public String getXmlFileName() {
        return this.xmlFileName;
    }

}
