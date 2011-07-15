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
import java.util.Collection;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This is the interface of a class which can import modelDefinitions from files.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface JMadModelDefinitionImporter {

    /**
     * returns all model definitions contained in the given file. If this is an xml file then this gives clearly only
     * one. Nevertheless in a zip there might be multiple.
     * 
     * @param file the file from which to load the model definitions
     * @return the model definitions
     */
    public abstract Collection<JMadModelDefinition> importModelDefinitions(File file);

    /**
     * This is a convenience method an in principle a shortcut to the first occurence int
     * {@link #importModelDefinitions(File)}. If there are multiple definitions in the file then the result might be
     * random!
     * 
     * @param file the file from which to load the model definition
     * @return one model definition
     */
    public abstract JMadModelDefinition importModelDefinition(File file);
}
