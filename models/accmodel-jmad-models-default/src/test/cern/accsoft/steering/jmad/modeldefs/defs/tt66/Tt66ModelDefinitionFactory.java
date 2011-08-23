/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.tt66;

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
 * This class is the actual model configuration for the TI2 transfer line.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class Tt66ModelDefinitionFactory implements ModelDefinitionFactory {

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName("TT66");

        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("tt66");
        modelDefinition.setModelPathOffsets(offsets);

        modelDefinition.addInitFile(new CallableModelFileImpl("beam.madx", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("tt66-v10-juan.seq", ModelFileLocation.RESOURCE));

        /*
         * OPTICS
         */
        String[] strengthFileNames = new String[] { "out.tt66-v10-0_10mm-0_10mm.str", "out.tt66-v10-0_20mm-0_20mm.str",
                "out.tt66-v10-0_50mm-0_50mm.str", "out.tt66-v10-1_0mm-1_0mm.str", "out.tt66-v10-1_50mm-1_50mm.str",
                "out.tt66-v10-2_0mm-2_0mm.str" };

        for (String strengthFileName : strengthFileNames) {
            String opticsName = strengthFileName.replaceAll("out.tt66-v10-", "").replaceAll(".str", "")
                    .replaceAll("_", ".");

            OpticsDefinition opticsDefinition = new OpticsDefinitionImpl(opticsName,
                    new ModelFile[] { new CallableModelFileImpl("str/" + strengthFileName, ModelFileLocation.RESOURCE,
                            ParseType.STRENGTHS) });
            modelDefinition.addOpticsDefinition(opticsDefinition);
        }
        modelDefinition.setDefaultOpticsDefinition(modelDefinition.getOpticsDefinitions().get(0));

        /*
         * SEQUENCE
         */

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl tt66 = new SequenceDefinitionImpl("tt66", BeamFactory.createDefaultLhcBeam());
        modelDefinition.setDefaultSequenceDefinition(tt66);
        RangeDefinitionImpl tt66range = new RangeDefinitionImpl(tt66, "ALL", createTi2Twiss());
        tt66.setDefaultRangeDefinition(tt66range);

        return modelDefinition;
    }

    /**
     * Twiss initial conditions for transferline Ti2
     */
    private final TwissInitialConditionsImpl createTi2Twiss() {
        TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl("tt66-twiss");

        /*
         * values from Juan (The same as for TI2)
         * 
         * 
         * sps.BETX0 = 17.02748544; sps.ALFX0 = 0.4583574683; sps.DX0 =-0.3408152943; sps.DPX0 = 0.01307653962;
         * sps.BETY0 = 123.9323528; sps.ALFY0 =-3.422196857; sps.DY0 = 0; sps.DPY0 = 0;
         */

        twiss.setDeltap(0.0);
        twiss.setBetx(17.02748544);
        twiss.setAlfx(0.4583574683);
        twiss.setDx(-0.3408152943);
        twiss.setDpx(0.01307653962);
        twiss.setBety(123.9323528);
        twiss.setAlfy(-3.422196857);
        twiss.setDy(0.0);
        twiss.setDpy(0.0);

        return twiss;

    }

}
