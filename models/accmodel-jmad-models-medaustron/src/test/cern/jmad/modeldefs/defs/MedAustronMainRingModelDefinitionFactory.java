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
public class MedAustronMainRingModelDefinitionFactory implements ModelDefinitionFactory {

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName("MedAustron Main Ring");

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("ma");
        modelDefinition.setModelPathOffsets(offsets);

        modelDefinition.addInitFile(new CallableModelFileImpl("common/elements.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("mr/mr.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("mr/mr.seq", ModelFileLocation.RESOURCE));

        /*
         * OPTICS
         */
        OpticsDefinition injectionOptics = createOptics("injection", "mr/mr-inj.str");
        modelDefinition.setDefaultOpticsDefinition(injectionOptics);
        
        OpticsDefinition extractionOptics = createOptics("extraction", "mr/mr-extr.str");
        modelDefinition.addOpticsDefinition(extractionOptics);

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

    private OpticsDefinition createOptics(String opticsName, String specificFileName) {
        return new OpticsDefinitionImpl(opticsName, new ModelFile[] {
                new CallableModelFileImpl(specificFileName, ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                new CallableModelFileImpl("mr/mr-corrector.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
    }
}
