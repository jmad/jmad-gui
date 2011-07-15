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

package cern.accsoft.steering.jmad.factory;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public interface JMadModelFactory {

    /**
     * creates a jmad model for the given {@link JMadModelDefinition} and configures it.
     * 
     * @param modelDefinition the {@link JMadModelDefinition} for which to create a model.
     * @return a fully configured jmad model.
     */
    public abstract JMadModel createModel(JMadModelDefinition modelDefinition);

    /**
     * adds the default knobs to the model
     * 
     * @param model the model to configure
     */
    public abstract void createDefaultKnobs(JMadModel model);

}
