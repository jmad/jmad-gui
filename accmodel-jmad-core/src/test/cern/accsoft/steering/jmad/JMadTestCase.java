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
