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

/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io.impl;

import java.io.File;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.file.AbstractModelFile;
import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.TableModelFileImpl;
import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.filter.RegexNameFilter;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsConverter;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.xml.GenericXStreamService;
import cern.accsoft.steering.jmad.util.xml.PersistenceServiceException;
import cern.accsoft.steering.jmad.util.xml.converters.GenericFieldAttributeConverter;

import com.thoughtworks.xstream.XStream;

/**
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class XmlModelDefinitionPersistenceService extends GenericXStreamService<JMadModelDefinition> implements
        ModelDefinitionPersistenceService {
    private static final Logger LOGGER = Logger.getLogger(XmlModelDefinitionPersistenceService.class);

    public XmlModelDefinitionPersistenceService() {
        super();
        configureXStream();
    }

    private void configureXStream() {
        XStream xStream = getXStream();

        /* first the converter */
        xStream.registerConverter(new TwissInitialConditionsConverter());
        xStream.registerConverter(new GenericFieldAttributeConverter<Beam>(xStream, Beam.class));
        xStream.registerConverter(new GenericFieldAttributeConverter<ModelPathOffsetsImpl>(xStream,
                ModelPathOffsetsImpl.class));

        /* and process the annotations of all classes we need */
        xStream.autodetectAnnotations(true);
        Class<?>[] classes = new Class<?>[] { JMadModelDefinitionImpl.class, //
                CallableModelFileImpl.class, //
                AbstractModelFile.class, //
                TableModelFileImpl.class, //
                Beam.class, //
                SequenceDefinitionImpl.class, //
                RangeDefinitionImpl.class, //
                OpticsDefinitionImpl.class, //
                MadxRange.class, //
                ModelPathOffsetsImpl.class, //
                RegexNameFilter.class };
        xStream.processAnnotations(classes);

        /*
         * To avoid using references
         */
        xStream.addImmutableType(CallableModelFileImpl.class);

        /* The default implementations */
        xStream.addDefaultImplementation(ModelPathOffsetsImpl.class, ModelPathOffsets.class);

    }

    @Override
    public File save(JMadModelDefinition modelDefinition, File file) throws PersistenceServiceException {
        if (!(modelDefinition instanceof JMadModelDefinitionImpl)) {
            LOGGER.error("can only save model definitions of type '" + JMadModelDefinitionImpl.class.getCanonicalName()
                    + "' to xml file.");
            return null;
        }

        return super.save(modelDefinition, file);
    }

    @Override
    public String getFileExtension() {
        return ModelDefinitionUtil.XML_FILE_EXTENSION;
    }

}
