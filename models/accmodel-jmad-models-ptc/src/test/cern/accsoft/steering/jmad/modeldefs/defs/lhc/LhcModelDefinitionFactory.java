/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.beam.Beam.Direction;
import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.TableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;

/**
 * The model definition for the LHC model
 * 
 * @author kfuchsbe
 */
public class LhcModelDefinitionFactory implements ModelDefinitionFactory {

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName("LHC PTC");

        modelDefinition.setModelPathOffsets(createModelPathOffsets());
        modelDefinition.addInitFile(new CallableModelFileImpl("set-options.madx", ModelFileLocation.RESOURCE));
        modelDefinition.addInitFile(new CallableModelFileImpl("V6.5.seq"));
        modelDefinition.addInitFile(new CallableModelFileImpl("rotate-to-IP3.madx", ModelFileLocation.RESOURCE));

        List<OpticsDefinition> opticsDefinitions = createOpticsDefinitions();
        for (OpticsDefinition opticsDefinition : opticsDefinitions) {
            modelDefinition.addOpticsDefinition(opticsDefinition);
        }
        modelDefinition.setDefaultOpticsDefinition(opticsDefinitions.get(0));

        /*
         * definition for beam 1
         */

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl lhcb1 = new SequenceDefinitionImpl("lhcb1", createBeam(Direction.PLUS));
        modelDefinition.addSequenceDefinition(lhcb1);
        modelDefinition.setDefaultSequenceDefinition(lhcb1);

        RangeDefinition allRange = createB1Range(lhcb1, "ALL", "#s", "#e", createDefaultTwiss());
        lhcb1.addRangeDefinition(allRange);
        lhcb1.setDefaultRangeDefinition(allRange);

        return modelDefinition;
    }
    
    private ModelPathOffsets createModelPathOffsets() {
        ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
        offsets.setResourceOffset("lhc");
        offsets.setRepositoryOffset("lhc/optics/V6.503");
        return offsets;
    }

    private List<OpticsDefinition> createOpticsDefinitions() {
        List<OpticsDefinition> opticsDefinitions = new ArrayList<OpticsDefinition>();

        ModelFile[] modelFiles = new ModelFile[] { //
                new CallableModelFileImpl("V6.5.inj.str", ModelFileLocation.REPOSITORY, ParseType.STRENGTHS), //
                new CallableModelFileImpl("create-nominal-b1.madx", ModelFileLocation.RESOURCE), //
                new TableModelFileImpl("ERRORS/alignment_errors/LHC-egeoc-b1.tfs", ModelFileLocation.RESOURCE), //
                new CallableModelFileImpl("assign-alignment-errors.madx", ModelFileLocation.RESOURCE), //
                new CallableModelFileImpl("measured_errors/Msubroutines.madx", ModelFileLocation.REPOSITORY), //
                new TableModelFileImpl("measured_errors/rotations_Q2_integral.tab", ModelFileLocation.REPOSITORY), //
                new CallableModelFileImpl("disable-common-area-correctors.madx", ModelFileLocation.RESOURCE), //
                new CallableModelFileImpl("CORRECTIONS/orbit_correction/correctors_sep_bumps.madx",
                        ModelFileLocation.RESOURCE), //
                new CallableModelFileImpl("correct-orbit-to-nominal.madx", ModelFileLocation.RESOURCE), //
                new CallableModelFileImpl("set-corrections-b1.madx", ModelFileLocation.RESOURCE) };

        OpticsDefinitionImpl opticsDefinition = new OpticsDefinitionImpl("INJ-B1-SEED1", modelFiles);

        opticsDefinition
                .addPostPtcUniverseFile(new TableModelFileImpl(
                        "ERRORS/absolute_harmonics/BEAM1/harmonics_MB_O2-11_S1.tfs", ModelFileLocation.RESOURCE,
                        "errors_read"));
        opticsDefinition.addPostPtcUniverseFile(new CallableModelFileImpl("ptc-readerrors-overwrite.madx",
                ModelFileLocation.RESOURCE));
        opticsDefinition
                .addPostPtcUniverseFile(new TableModelFileImpl(
                        "ERRORS/absolute_harmonics/BEAM1/harmonics_MQ_O2-11_S1.tfs", ModelFileLocation.RESOURCE,
                        "errors_read"));
        opticsDefinition.addPostPtcUniverseFile(new CallableModelFileImpl("ptc-readerrors.madx",
                ModelFileLocation.RESOURCE));
        opticsDefinition.addPostPtcUniverseFile(new TableModelFileImpl(
                "CORRECTIONS/BEAM1/ptc_total_corr1_b1_MC_O2-11_S1.tfs", ModelFileLocation.RESOURCE, "errors_read"));
        opticsDefinition.addPostPtcUniverseFile(new CallableModelFileImpl("ptc-readerrors-overwrite.madx",
                ModelFileLocation.RESOURCE));

        opticsDefinitions.add(opticsDefinition);

        return opticsDefinitions;
    }

    private final RangeDefinitionImpl createB1Range(SequenceDefinition sequenceDefinition, String name, String start,
            String end, TwissInitialConditionsImpl twiss) {
        RangeDefinitionImpl rangeDefinition = new RangeDefinitionImpl(sequenceDefinition, name, new MadxRange(start,
                end), twiss);
        return rangeDefinition;
    }

    private final TwissInitialConditionsImpl createDefaultTwiss() {
        return new TwissInitialConditionsImpl("default-twiss");
    }

    private final Beam createBeam(Beam.Direction direction) {
        Beam beam = BeamFactory.createDefaultLhcBeam();
        beam.setDirection(direction);
        return beam;
    }

}
