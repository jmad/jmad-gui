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
package cern.accsoft.steering.jmad.modeldefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelFileFinderImpl;

/**
 * This is the default implementation of a class that knows about the available Model definitions. It uses certain
 * finders to search for model definitions and stores them internally for further retrieval.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public abstract class JMadModelDefinitionManagerImpl implements JMadModelDefinitionManager {

    /**
     * A list of all available model definitions. This is set to null on purpose. So it is loaded the first time we need
     * it.
     */
    private List<JMadModelDefinition> modelDefinitions = null;

    /**
     * this list will be walked through and every finder is used to search for modelDefinitions.
     */
    private List<ModelDefinitionFinder> finders = new ArrayList<ModelDefinitionFinder>();

    /** The hashmap for the fileFinders */
    private final Map<JMadModelDefinition, ModelFileFinder> modelFileFinders = new WeakHashMap<JMadModelDefinition, ModelFileFinder>();

    private List<JMadModelDefinition> findAllModelDefinitions() {
        List<JMadModelDefinition> modelDefs = new ArrayList<JMadModelDefinition>();
        for (ModelDefinitionFinder finder : this.getFinders()) {
            modelDefs.addAll(finder.findAllModelDefinitions());
        }
        return modelDefs;
    }

    @Override
    public List<JMadModelDefinition> getAllModelDefinitions() {
        /*
         * lazy loading of the model definitions
         */
        if (this.modelDefinitions == null) {
            this.modelDefinitions = findAllModelDefinitions();
        }
        return this.modelDefinitions;
    }

    @Override
    public JMadModelDefinition getModelDefinition(String name, boolean ignoreCase) {
        List<JMadModelDefinition> foundModelDefinitions = getAllModelDefinitions();

        for (JMadModelDefinition modelDefinition : foundModelDefinitions) {
            if (name.equals(modelDefinition.getName())) {
                return modelDefinition;
            }
            if (ignoreCase && (name.equalsIgnoreCase(modelDefinition.getName()))) {
                return modelDefinition;
            }
        }
        return null;

    }

    @Override
    public JMadModelDefinition getModelDefinition(String name) {
        return getModelDefinition(name, false);
    }

    public void setFinders(List<ModelDefinitionFinder> finders) {
        this.finders = finders;
    }

    private List<ModelDefinitionFinder> getFinders() {
        return finders;
    }

    @Override
    public ModelFileFinder getModelFileFinder(JMadModelDefinition modelDefinition) {
        ModelFileFinder fileFinder = this.modelFileFinders.get(modelDefinition);
        if (fileFinder == null) {
            fileFinder = createModelFileFinder(modelDefinition);
            this.modelFileFinders.put(modelDefinition, fileFinder);
        }
        return fileFinder;
    }

    /**
     * creates a new modelFileFinder for a model definition
     * 
     * @param modelDefinition the model definition for which to create a {@link ModelFileFinder}
     * @return the fully configured {@link ModelFileFinder}
     */
    private ModelFileFinder createModelFileFinder(JMadModelDefinition modelDefinition) {
        ModelFileFinderImpl fileFinder = createModelFileFinder();
        fileFinder.setModelFilePathOffsets(modelDefinition.getModelPathOffsets());
        fileFinder.setSourceInformation(modelDefinition.getSourceInformation());
        return fileFinder;
    }

    /**
     * lookup method injected by spring
     * 
     * @return a fully configured ModelFileFinder
     */
    protected abstract ModelFileFinderImpl createModelFileFinder();

}
