/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.jmad.modeldefs.defs;

import java.util.List;

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
 * @author kfuchsbe
 */
public abstract class AbstractMebtModelFactory implements ModelDefinitionFactory {

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName("MedAustron MEBT (" + getModelSuffix() + ")");

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("ma");
        modelDefinition.setModelPathOffsets(offsets);

        modelDefinition.addInitFile(new CallableModelFileImpl("common/elements.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("me/me.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("me/me.seq", ModelFileLocation.RESOURCE));

        /*
         * OPTICS
         */
        OpticsDefinition defaultDefinition = null;
        for (String name : getQuadStrengthFileNames()) {
            OpticsDefinition optics = new OpticsDefinitionImpl(name, new ModelFile[] {
                    new CallableModelFileImpl("me/" + name + ".str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                    new CallableModelFileImpl("me/me-corrector.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
            if (defaultDefinition == null) {
                defaultDefinition = optics;
            }
            modelDefinition.addOpticsDefinition(optics);
        }
        modelDefinition.setDefaultOpticsDefinition(defaultDefinition);

        /*
         * SEQUENCE
         */

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl me = new SequenceDefinitionImpl("me", new Beam());
        modelDefinition.setDefaultSequenceDefinition(me);

        RangeDefinitionImpl all = new RangeDefinitionImpl(me, "ALL", createInititalConditions());
        me.setDefaultRangeDefinition(all);

        return modelDefinition;
    }

    protected abstract String getModelSuffix();

    protected abstract List<String> getQuadStrengthFileNames();

    protected abstract TwissInitialConditionsImpl createInititalConditions();

}