package cern.accsoft.steering.jmad.modeldefs.io.impl;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsJsonConverter;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.util.xml.GenericXStreamService;
import cern.accsoft.steering.jmad.util.xml.JsonXStreamService;

import com.thoughtworks.xstream.XStream;

public class JsonModelDefinitionPersistenceService extends AbstractModelDefinitionPersistenceService {

    JsonXStreamService<JMadModelDefinition> xStreamService = new JsonXStreamService<JMadModelDefinition>() {

        @Override
        protected void initializeXStream(XStream xstream) {
            /* first the converter */
            xstream.registerConverter(new TwissInitialConditionsJsonConverter());

            /* then the super class initialization */
            configureXStream(xstream);
        }

        @Override
        protected Class<? extends JMadModelDefinition> getSaveableClass() {
            return getSaveableClass();
        }

        @Override
        public String getFileExtension() {
            return ModelDefinitionUtil.JSON_FILE_EXTENSION;
        }
    };

    @Override
    protected GenericXStreamService<JMadModelDefinition> getXStreamService() {
        return this.xStreamService;
    }

}
