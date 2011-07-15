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

package cern.accsoft.steering.jmad;

import cern.accsoft.steering.jmad.modeldefs.ClassPathModelDefinitionFinder;
import cern.accsoft.steering.jmad.modeldefs.JMadModelDefinitionManager;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;
import cern.accsoft.steering.jmad.util.LoggedTestCase;

/***
 * this is a general testcase for jmad-stuff. It provides a convenience-method to fetch the example model.
 * 
 * @author kfuchsbe
 */
public abstract class JMadTestCase extends LoggedTestCase {

    private static JMadService jmadService = JMadServiceFactory.createJMadService();

    /**
     * just uses the {@link ClassPathModelDefinitionFinder} to get the example model definition.
     * 
     * @return the example model-definition.
     */
    protected static JMadModelDefinition findExampleModelDefinition() {
        JMadModelDefinitionManager manager = jmadService.getModelDefinitionManager();
        JMadModelDefinition modelDefinition = manager.getModelDefinition("example", false);
        return modelDefinition;
    }

    protected static JMadModelDefinition findThinLensExampleModelDefinition() {
        JMadModelDefinitionManager manager = jmadService.getModelDefinitionManager();
        JMadModelDefinition modelDefinition = manager.getModelDefinition("example_THIN", false);
        return modelDefinition;
    }

    protected static JMadService getJMadService() {
        return jmadService;
    }
}
