package cern.accsoft.steering.jmad.modeldefs.io.impl;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsJsonConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class JsonModelDefinitionPersistenceService extends
		AbstractModelDefinitionPersistenceService {

	@Override
	public String getFileExtension() {
		return ModelDefinitionUtil.JSON_FILE_EXTENSION;
	}

	@Override
	protected XStream createXStream() {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());

		/* first the converter */
		xstream.registerConverter(new TwissInitialConditionsJsonConverter());

		configureXStream(xstream);
		return xstream;
	}

}
