/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.ti8;

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
 * The model definition for the TI8-transfer line
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public abstract class Ti8ModelDefinition08Factory implements ModelDefinitionFactory {

    protected abstract String getName();

    protected abstract void addOpticsDefinitions(JMadModelDefinitionImpl modelDefinition);

    protected abstract TwissInitialConditionsImpl createTi8Twiss();

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName(getName());

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("ti8/ti8-2008");
        modelDefinition.setModelPathOffsets(offsets);

        modelDefinition.addInitFile(new CallableModelFileImpl("TI8_140808_k1_k2.seq", ModelFileLocation.RESOURCE));

        addOpticsDefinitions(modelDefinition);

        /*
         * SEQUENCE
         */

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl ti8 = new SequenceDefinitionImpl("ti8", BeamFactory.createDefaultLhcBeam());
        modelDefinition.setDefaultSequenceDefinition(ti8);

        RangeDefinitionImpl range = new RangeDefinitionImpl(ti8, "ALL", createTi8Twiss());
        ti8.setDefaultRangeDefinition(range);

        return modelDefinition;
    }

    protected OpticsDefinition createOpticsDefinition(String name, String ti8StrengthFileName) {
        return new OpticsDefinitionImpl(name, new ModelFile[] {
                new CallableModelFileImpl(ti8StrengthFileName, ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                new CallableModelFileImpl("ti8-commons.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
    }

}
