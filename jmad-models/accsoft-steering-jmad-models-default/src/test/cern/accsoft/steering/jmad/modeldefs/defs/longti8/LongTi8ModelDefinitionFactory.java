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
import cern.accsoft.steering.jmad.domain.machine.filter.RegexNameFilter;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.factory.BeamFactory;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;

/**
 * The model definition for a model covering TI8 + LHC sector 78
 * 
 * @author kfuchsbe
 */
public class LongTi8ModelDefinitionFactory implements ModelDefinitionFactory {

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();

        modelDefinition.setName("longti8");

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("longti8/longti8");
        modelDefinition.setModelPathOffsets(offsets);

        modelDefinition.addInitFile(new CallableModelFileImpl("ti8.seq", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("beam_four.seq", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("join-ti8-lhc.madx", ModelFileLocation.RESOURCE));

        OpticsDefinition opticsDefinitions = new OpticsDefinitionImpl("default optics", new ModelFile[] {
                new CallableModelFileImpl("ti8.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
                new CallableModelFileImpl("V6.5.inj.str", ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
        modelDefinition.setDefaultOpticsDefinition(opticsDefinitions);

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl longti8 = new SequenceDefinitionImpl("ti8lhcb2", BeamFactory.createDefaultLhcBeam());
        modelDefinition.setDefaultSequenceDefinition(longti8);

        RangeDefinitionImpl b2range = new RangeDefinitionImpl(longti8, "ALL", createTi8Twiss());
        /* XXX invert the X-bpms */
        b2range.addMonitorInvertFilter(new RegexNameFilter("(?i)^BPM.*\\.B2", JMadPlane.H));
        b2range.addMonitorInvertFilter(new RegexNameFilter("(?i)^BTV.*", JMadPlane.H));
        /* invert the x-correctors */
        b2range.addCorrectorInvertFilter(new RegexNameFilter("(?i)^MCB.*\\.B2", JMadPlane.H));

        b2range.addPostUseFile(new CallableModelFileImpl("set-errors-b2-b3.madx", ModelFileLocation.RESOURCE));

        longti8.setDefaultRangeDefinition(b2range);
        longti8.addRangeDefinition(b2range);

        return modelDefinition;
    }

    /*
     * betx = 17.06248574,alfx =0.4588913839, bety=124.8219205, alfy=-3.444554843, dx= -0.2527900995,dpx= 0.00334144588;
     */
    private final TwissInitialConditionsImpl createTi8Twiss() {
        TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl("ti8-twiss");

        twiss.setDeltap(0.000);
        twiss.setBetx(17.06248574);
        twiss.setAlfx(0.4588913839);
        twiss.setDx(-0.2527900995);
        twiss.setDpx(0.00334144588);
        twiss.setBety(124.8219205);
        twiss.setAlfy(-3.444554843);
        twiss.setDy(0.0);
        twiss.setDpy(0.0);

        return twiss;
    }
}
