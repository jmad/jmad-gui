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

package cern.accsoft.steering.jmad.modeldefs;

import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.impl.XmlModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.LoggedTestCase;

public class ClassPathModelDefinitionFinderTest extends LoggedTestCase {

    @Test
    public void testFindAllModelDefinitions() {

        ClassPathModelDefinitionFinder finder = new ClassPathModelDefinitionFinder();
        finder.setPersistenceService(new XmlModelDefinitionPersistenceService());

        List<JMadModelDefinition> modelDefinitions = finder.findAllModelDefinitions();

        assertTrue("We should find at least one modelDefinition.", modelDefinitions.size() > 0);
        assertTrue("The model definition should be on of the example model definitions", modelDefinitions.get(0)
                .getName().equals("example_THIN")
                || modelDefinitions.get(0).getName().equals("example"));

    }

}
