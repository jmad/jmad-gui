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
package cern.accsoft.steering.jmad;

import cern.accsoft.steering.jmad.modeldefs.ClassPathModelDefinitionFinder;
import cern.accsoft.steering.jmad.modeldefs.JMadModelDefinitionManager;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;
import cern.accsoft.steering.jmad.util.LoggedTestCase;

/***
 * this is a general testcase for jmad-stuff. It provides a convenience-method
 * to fetch the example model.
 * 
 * @author kfuchsbe
 * 
 */
public abstract class JMadTestCase extends LoggedTestCase {

	private static JMadService jmadService = JMadServiceFactory
			.createJMadService();

	/**
	 * just uses the {@link ClassPathModelDefinitionFinder} to get the example
	 * model definition.
	 * 
	 * @return the example model-definition.
	 */
	protected static JMadModelDefinition findExampleModelDefinition() {
		JMadModelDefinitionManager manager = jmadService
				.getModelDefinitionManager();
		JMadModelDefinition modelDefinition = manager.getModelDefinition(
				"example", false);
		return modelDefinition;
	}
	
	protected static JMadModelDefinition findThinLensExampleModelDefinition() {
		JMadModelDefinitionManager manager = jmadService
			.getModelDefinitionManager();
		JMadModelDefinition modelDefinition = manager.getModelDefinition(
				"example_THIN", false);
		return modelDefinition;
	}

	protected static JMadService getJMadService() {
		return jmadService;
	}
}
