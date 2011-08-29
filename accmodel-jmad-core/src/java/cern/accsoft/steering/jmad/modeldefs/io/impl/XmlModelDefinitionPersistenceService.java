// @formatter:off
/*******************************************************************************
 * This file is part of JMad. Copyright (c) 2008-2011, CERN. All rights reserved. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io.impl;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsXmlConverter;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.util.xml.GenericXStreamService;
import cern.accsoft.steering.jmad.util.xml.XmlXStreamService;
import cern.accsoft.steering.jmad.util.xml.converters.GenericFieldAttributeConverter;

import com.thoughtworks.xstream.XStream;

/**
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class XmlModelDefinitionPersistenceService extends AbstractModelDefinitionPersistenceService {

    private XmlXStreamService<JMadModelDefinition> xStreamService = new XmlXStreamService<JMadModelDefinition>() {

        @Override
        protected void initializeXStream(XStream xstream) {
            performXStreamInitialization(xstream);
        }

        @Override
        protected Class<? extends JMadModelDefinition> getSaveableClass() {
            return retrieveSavableClass();
        }

        @Override
        public String getFileExtension() {
            return ModelDefinitionUtil.XML_FILE_EXTENSION;
        }
    };

    protected void performXStreamInitialization(XStream xStream) {
        /* first the converter */
        xStream.registerConverter(new TwissInitialConditionsXmlConverter());
        xStream.registerConverter(new GenericFieldAttributeConverter<Beam>(xStream, Beam.class));
        xStream.registerConverter(new GenericFieldAttributeConverter<ModelPathOffsetsImpl>(xStream,
                ModelPathOffsetsImpl.class));

        /* then the super class initialization */
        super.configureXStream(xStream);
    }

    protected Class<? extends JMadModelDefinition> retrieveSavableClass() {
        return super.getSaveableClass();
    }

    @Override
    protected GenericXStreamService<JMadModelDefinition> getXStreamService() {
        return this.xStreamService;
    }

}
