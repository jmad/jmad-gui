package cern.accsoft.steering.jmad.modeldefs.io.impl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;

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
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.xml.GenericXStreamService;
import cern.accsoft.steering.jmad.util.xml.PersistenceService;
import cern.accsoft.steering.jmad.util.xml.PersistenceServiceException;

public abstract class AbstractModelDefinitionPersistenceService implements ModelDefinitionPersistenceService {
    @Override
    public JMadModelDefinition clone(JMadModelDefinition object) throws PersistenceServiceException {
        return this.getXStreamService().clone(object);
    }

    @Override
    public String getFileExtension() {
        return this.getXStreamService().getFileExtension();
    }

    @Override
    public boolean isCorrectFileName(String fileName) {
        return this.getXStreamService().isCorrectFileName(fileName);
    }

    @Override
    public JMadModelDefinition load(File file) throws PersistenceServiceException {
        return this.getXStreamService().load(file);
    }

    @Override
    public JMadModelDefinition load(InputStream inputStream) throws PersistenceServiceException {
        return this.getXStreamService().load(inputStream);
    }

    @Override
    public File save(JMadModelDefinition object, File file) throws PersistenceServiceException {
        return this.getXStreamService().save(object, file);
    }

    @Override
    public void save(JMadModelDefinition object, OutputStream outStream) throws PersistenceServiceException {
        this.getXStreamService().save(object, outStream);
    }

    /**
     * @return the {@link GenericXStreamService} to use in this {@link PersistenceService} for the
     *         {@link JMadModelDefinition}s
     */
    protected abstract GenericXStreamService<JMadModelDefinition> getXStreamService();

    protected Class<JMadModelDefinitionImpl> getSaveableClass() {
        return JMadModelDefinitionImpl.class;
    }

    protected final void configureXStream(XStream xStream) {
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
}