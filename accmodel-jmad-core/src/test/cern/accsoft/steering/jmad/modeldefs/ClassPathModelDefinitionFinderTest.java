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
package cern.accsoft.steering.jmad.modeldefs;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.impl.XmlModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.LoggedTestCase;

public class ClassPathModelDefinitionFinderTest extends LoggedTestCase {

	@Test
	public void testFindAllModelDefinitions() {

		ClassPathModelDefinitionFinder finder = new ClassPathModelDefinitionFinder();
		finder
				.setPersistenceService(new XmlModelDefinitionPersistenceService());

		List<JMadModelDefinition> modelDefinitions = finder
				.findAllModelDefinitions();

		assertTrue("We should find at least one modelDefinition.",
				modelDefinitions.size() > 0);
		assertTrue("The model definition should be on of the example model definitions",
				modelDefinitions.get(0).getName().equals("example_THIN") ||
				modelDefinitions.get(0).getName().equals("example"));

	}

}
