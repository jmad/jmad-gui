package cern.jmad.modeldefs.defs;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;

/**
 * the model definition for med-austron
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class MedAustronModelDefinitionFactory implements ModelDefinitionFactory {

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName("MedAustron Synchrotron");

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("ma");
        modelDefinition.setModelPathOffsets(offsets);

        modelDefinition.addInitFile(new CallableModelFileImpl("elements.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("mr-elements.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("mr.seq", ModelFileLocation.RESOURCE));

        /*
         * OPTICS
         */
        OpticsDefinition opticsDefinition = new OpticsDefinitionImpl("default optics", new ModelFile[] {
                new CallableModelFileImpl("mr-extr.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                new CallableModelFileImpl("mr-corrector.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
        modelDefinition.setDefaultOpticsDefinition(opticsDefinition);

        /*
         * SEQUENCE
         */

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl mr = new SequenceDefinitionImpl("mr", new Beam());
        modelDefinition.setDefaultSequenceDefinition(mr);
        RangeDefinitionImpl mrRange = new RangeDefinitionImpl(mr, "ALL", new TwissInitialConditionsImpl());
        mr.setDefaultRangeDefinition(mrRange);

        return modelDefinition;
    }
}
