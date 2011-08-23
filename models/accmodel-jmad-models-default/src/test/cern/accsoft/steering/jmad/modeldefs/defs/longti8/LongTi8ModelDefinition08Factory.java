/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.longti8;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.factory.BeamFactory;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;

/**
 * The model definition for a model covering TI8 + LHC sector 78
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public abstract class LongTi8ModelDefinition08Factory implements ModelDefinitionFactory {

    protected abstract String getName();

    protected abstract void addOpticsDefinitions(JMadModelDefinitionImpl modelDefinition);

    protected abstract TwissInitialConditionsImpl createTi8Twiss();

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName(getName());

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("longti8/longti8-2008");
        modelDefinition.setModelPathOffsets(offsets);

        /* the ti8-stuff */
        modelDefinition.addInitFile(new CallableModelFileImpl("TI8_140808_k1_k2.seq", ModelFileLocation.RESOURCE));

        /* the lhc stuff + combining */
        modelDefinition.addInitFile(new CallableModelFileImpl("job1.madx", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("beam_four.seq", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("job.ti8lhcV6.503.optics.madx", ModelFileLocation.RESOURCE));

        addOpticsDefinitions(modelDefinition);

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl longti8 = new SequenceDefinitionImpl("ti8lhcb2", BeamFactory.createDefaultLhcBeam());
        modelDefinition.setDefaultSequenceDefinition(longti8);

        RangeDefinitionImpl b2range = new RangeDefinitionImpl(longti8, "ALL", createTi8Twiss());
        longti8.addRangeDefinition(b2range);
//        /* XXX invert the X-bpms */
//        b2range.addMonitorInvertFilter(new RegexNameFilter("(?i)^BPM.*\\.B2", JMadPlane.H));
//        b2range.addMonitorInvertFilter(new RegexNameFilter("(?i)^BTV.*", JMadPlane.H));

        longti8.setDefaultRangeDefinition(b2range);
        return modelDefinition;
    }

    protected OpticsDefinition createOpticsDefinition(String name, String ti8StrengthFileName) {
        return new OpticsDefinitionImpl(name, new ModelFile[] {
                new CallableModelFileImpl(ti8StrengthFileName, ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                new CallableModelFileImpl("ti8-commons.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                new CallableModelFileImpl("V6.5.inj.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
    }

}
