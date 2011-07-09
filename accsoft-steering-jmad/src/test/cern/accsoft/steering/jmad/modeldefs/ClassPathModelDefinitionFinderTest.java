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
