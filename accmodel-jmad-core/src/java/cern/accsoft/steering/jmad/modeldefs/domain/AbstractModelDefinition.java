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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;

/**
 * A Model definition consists of a (arbitrary) number of initialization - files (madx files) and contains an arbitrary
 * number of sequence-names. This is the abstract version of a model definition. It implements lazy loading for almost
 * all properties to ensure, that they are created only when they are needed, only once and reused afterwards. All the
 * create... methods have to be overridden by the subclasses. NOTE: It is important that that class stays at the same
 * resource-location, since all the resource-paths are calculated relative to this class!
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public abstract class AbstractModelDefinition implements JMadModelDefinition {

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(AbstractModelDefinition.class);

    @Override
    public final String toString() {
        return getName();
    }

    @Override
    public final SequenceDefinition getSequenceDefinition(String name) {
        SequenceDefinition sequenceDefinition = findSequenceDefinition(name);
        if (sequenceDefinition == null) {
            /* if we find nothing .. */
            LOGGER.warn("Sequence Definition [" + name + "] does not exist in ModelDefinition [" + this.getName() + "]");
        }
        return sequenceDefinition;
    }

    private SequenceDefinition findSequenceDefinition(String name) {
        if (name == null) {
            LOGGER.error("name of SequenceDefinition was null. Aborting search.");
            return null;
        }
        for (SequenceDefinition sequenceDefinition : getSequenceDefinitions()) {
            if (name.equals(sequenceDefinition.getName())) {
                return sequenceDefinition;
            }
        }

        return null;
    }

    @Override
    public List<RangeDefinition> getRangeDefinitions() {
        List<RangeDefinition> ranges = new ArrayList<RangeDefinition>();
        for (SequenceDefinition sequenceDefinition : getSequenceDefinitions()) {
            ranges.addAll(sequenceDefinition.getRangeDefinitions());
        }
        return ranges;
    }

    @Override
    public RangeDefinition getDefaultRangeDefinition() {
        SequenceDefinition defaultSequence = getDefaultSequenceDefinition();
        if (defaultSequence == null) {
            return null;
        }
        return defaultSequence.getDefaultRangeDefinition();
    }

    @Override
    public final OpticsDefinition getOpticsDefinition(String name) {
        OpticsDefinition opticsDefinition = findOpticsDefinition(name);
        if (opticsDefinition == null) {
            /* If nothing is found ... */
            LOGGER.warn("Optics Definition [" + name + "] does not exist in ModelDefinition [" + this.getName() + "]");
        }
        return opticsDefinition;
    }

    private OpticsDefinition findOpticsDefinition(String name) {
        if (name == null) {
            LOGGER.warn("Name of searched opticsDefinition was null. Aborting search.");
            return null;
        }
        for (OpticsDefinition opticsDefinition : getOpticsDefinitions()) {
            if (name.equals(opticsDefinition.getName())) {
                return opticsDefinition;
            }
        }
        return null;
    }

    protected boolean containsSequenceDefinition(String name) {
        return (findSequenceDefinition(name) != null);
    }

    protected boolean containsOpticsDefinition(String name) {
        return (findOpticsDefinition(name) != null);
    }

    /**
     * Returns all the {@link ModelFile}s used by this model definition.
     * <p>
     * <b>DANGER</b>: This does not return all required files, but only the init files, which is correct since this is
     * only the implementation for ModelFileDependant - interfsace. To get ALL the required files use
     * {@link ModelDefinitionUtil}
     * 
     * @return all the required model files
     */
    @Override
    public final Collection<ModelFile> getRequiredFiles() {
        return getInitFiles();
    }

}
