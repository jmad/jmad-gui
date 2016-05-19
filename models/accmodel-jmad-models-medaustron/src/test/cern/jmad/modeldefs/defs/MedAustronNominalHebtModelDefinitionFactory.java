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
public class MedAustronNominalHebtModelDefinitionFactory implements ModelDefinitionFactory {

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName("MedAustron HEBT (nominal)");

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("ma");
        modelDefinition.setModelPathOffsets(offsets);

        modelDefinition.addInitFile(new CallableModelFileImpl("common/elements.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("ex/ex.def", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("ex/ex.seq", ModelFileLocation.RESOURCE));

        /*
         * OPTICS
         */
        OpticsDefinition optics = new OpticsDefinitionImpl("nominal", new ModelFile[] {
                new CallableModelFileImpl("ex/ex.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                new CallableModelFileImpl("ex/ex-corrector.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
        modelDefinition.addOpticsDefinition(optics);
        modelDefinition.setDefaultOpticsDefinition(optics);

        /*
         * SEQUENCE
         */
        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl me = new SequenceDefinitionImpl("ex", new Beam());
        modelDefinition.setDefaultSequenceDefinition(me);

        RangeDefinitionImpl all = new RangeDefinitionImpl(me, "ALL", createInititalConditions());
        me.setDefaultRangeDefinition(all);

        return modelDefinition;
    }

    /**
     * Creates the initial conditions as given by A. Wastl, (2015-01-16):
     * <ul>
     * <li>ex.betx := 20.809181; // default: 20.809181
     * <li>ex.alfx := -1.016865; // default: -1.016865
     * <li>ex.dx := -4.465579; // default: -4.465579
     * <li>ex.dpx := -0.48949; // default: -0.48949
     * <li>ex.bety := 3.558261; // default: 3.558261
     * <li>ex.alfy := 0.723021; // default: 0.723021
     * <li>ex.dy := 0; // default: 0
     * <li>ex.dpy := 0; // default: 0
     * </ul>
     */
    protected TwissInitialConditionsImpl createInititalConditions() {
        TwissInitialConditionsImpl inititalConditions = new TwissInitialConditionsImpl();
        inititalConditions.setBetx(20.809181);
        inititalConditions.setAlfx(-1.016865);
        inititalConditions.setDx(-4.465579);
        inititalConditions.setDpx(-0.48949);

        inititalConditions.setBety(3.558261);
        inititalConditions.setAlfy(0.723021);
        inititalConditions.setDy(0.0);
        inititalConditions.setDpy(0.0);
        return inititalConditions;
    }

}
