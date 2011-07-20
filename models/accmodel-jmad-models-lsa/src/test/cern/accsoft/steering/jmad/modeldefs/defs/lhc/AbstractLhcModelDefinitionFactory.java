/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.beam.Beam.Direction;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.filter.RegexNameFilter;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.create.OpticDefinitionSet;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcUtil;

public abstract class AbstractLhcModelDefinitionFactory implements ModelDefinitionFactory {

    protected abstract String getModelDefinitionName();

    protected abstract ModelPathOffsets createModelPathOffsets();

    protected abstract void addInitFiles(JMadModelDefinitionImpl modelDefinition);

    /**
     * @return the list of optic definition sets which are defined in this factory
     */
    protected abstract List<OpticDefinitionSet> getOpticDefinitionSets();

    private List<OpticsDefinition> createOpticsDefinitions() {
        List<OpticsDefinition> opticsDefinitions = new ArrayList<OpticsDefinition>();

        for (OpticDefinitionSet definitionSet : this.getOpticDefinitionSets()) {
            for (String opticName : definitionSet.getDefinedOpticNames()) {

                /*
                 * first collect the model files
                 */
                List<ModelFile> modelFiles = new ArrayList<ModelFile>();
                modelFiles.addAll(definitionSet.getInitialCommonOpticFiles());
                modelFiles.addAll(definitionSet.getOpticModelFiles(opticName));
                modelFiles.addAll(definitionSet.getFinalCommonOpticFiles());

                /*
                 * next create the actual definition
                 */
                OpticsDefinition opticsDefinition = new OpticsDefinitionImpl(opticName, modelFiles
                        .toArray(new ModelFile[modelFiles.size()]));

                opticsDefinitions.add(opticsDefinition);
            }
        }

        return opticsDefinitions;
    }

    /**
     * Method to be overwritten by extending classes to add files that have to be called after the given range was used
     * in MADX.
     * 
     * @param rangeDefinition
     */
    protected abstract void addPostUseFiles(RangeDefinitionImpl rangeDefinition);

    @Override
    public JMadModelDefinition create() {
        JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
        modelDefinition.setName(this.getModelDefinitionName());

        modelDefinition.setModelPathOffsets(createModelPathOffsets());

        this.addInitFiles(modelDefinition);

        List<OpticsDefinition> opticsDefinitions = createOpticsDefinitions();
        for (OpticsDefinition opticsDefinition : opticsDefinitions) {
            modelDefinition.addOpticsDefinition(opticsDefinition);
        }
        modelDefinition.setDefaultOpticsDefinition(opticsDefinitions.get(0));

        /*
         * definition for beam 1
         */

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl lhcb1 = new SequenceDefinitionImpl(LhcUtil.SEQUENCE_NAME_BEAM1,
                createBeam(Direction.PLUS));
        modelDefinition.addSequenceDefinition(lhcb1);
        modelDefinition.setDefaultSequenceDefinition(lhcb1);

        RangeDefinition allRange = createB1Range(lhcb1, LhcUtil.DEFAULT_RANGE_NAME, "#s", "#e", createDefaultTwiss());
        lhcb1.addRangeDefinition(allRange);
        lhcb1.setDefaultRangeDefinition(allRange);

        lhcb1.addRangeDefinition(createB1Range(lhcb1, "ALL first turn", "#s", "#e", createLhcB1InjTwiss()));
        lhcb1.addRangeDefinition(createB1Range(lhcb1, "Inj. Sector 2-3", "LHCINJ.B1", "TCLA.7R3.B1",
                createLhcB1InjTwiss()));
        lhcb1.addRangeDefinition(createB1Range(lhcb1, "Inj. Sector 2-5", "LHCINJ.B1", "IP5", createLhcB1InjTwiss()));

        /*
         * definition for beam 2
         */

        Beam beam2 = createBeam(Direction.MINUS);

        /* NOTE: sequenceName must correspond to the name in .seq - file! */
        SequenceDefinitionImpl lhcb2 = new SequenceDefinitionImpl(LhcUtil.SEQUENCE_NAME_BEAM2, beam2);
        modelDefinition.addSequenceDefinition(lhcb2);

        RangeDefinition allRangeB2 = createB2Range(lhcb2, LhcUtil.DEFAULT_RANGE_NAME, "#s", "#e", createDefaultTwiss());
        lhcb2.addRangeDefinition(allRangeB2);
        lhcb2.setDefaultRangeDefinition(allRangeB2);

        lhcb2.addRangeDefinition(createB2Range(lhcb2, "ALL first turn", "#s", "#e", createLhcB2InjTwiss()));
        lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-7", "TCLA.A7L7.B2", "LHCINJ.B2",
                createLhcB2InjTwiss()));
        lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-6", "TCDQA.A4L6.B2", "LHCINJ.B2",
                createLhcB2InjTwiss()));
        lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-5", "IP5", "LHCINJ.B2", createLhcB2InjTwiss()));
        lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-4", "IP4", "LHCINJ.B2", createLhcB2InjTwiss()));
        lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-1", "IP1", "LHCINJ.B2", createLhcB2InjTwiss()));

        return modelDefinition;
    }

    private final RangeDefinitionImpl createB1Range(SequenceDefinition sequenceDefinition, String name, String start,
            String end, TwissInitialConditionsImpl twiss) {
        RangeDefinitionImpl rangeDefinition = new RangeDefinitionImpl(sequenceDefinition, name, new MadxRange(start,
                end), twiss);
        // rangeDefinition.setApertureDefinition(createB1ApertureDefinition());
        addPostUseFiles(rangeDefinition);
        return rangeDefinition;
    }

    private final RangeDefinitionImpl createB2Range(SequenceDefinition sequenceDefinition, String name, String start,
            String end, TwissInitialConditionsImpl twiss) {
        RangeDefinitionImpl rangeDefinition = new RangeDefinitionImpl(sequenceDefinition, name, new MadxRange(start,
                end), twiss);
        /* invert all MCBXes (?i) lets it ignore the case */
        rangeDefinition.addCorrectorInvertFilter(new RegexNameFilter("(?i)^MCBX.*", JMadPlane.H));
        rangeDefinition.addCorrectorInvertFilter(new RegexNameFilter("(?i)^MCBX.*", JMadPlane.V));
        addPostUseFiles(rangeDefinition);
        return rangeDefinition;
    }

    private final TwissInitialConditionsImpl createDefaultTwiss() {
        TwissInitialConditionsImpl twissInitialConditions = new TwissInitialConditionsImpl("lsa-definition");

        /* these definitions are taken from an old script created by marek */
        twissInitialConditions.setClosedOrbit(true);
        twissInitialConditions.setCalcAtCenter(true);
        twissInitialConditions.setCalcChromaticFunctions(true);

        return twissInitialConditions;
    }

    /**
     * the twiss for LHC injection in point 2
     * 
     * @return
     */
    private final TwissInitialConditionsImpl createLhcB1InjTwiss() {
        TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl("lhcb1-twiss");

        twiss.setDeltap(0.0);
        twiss.setBetx(55.679041);
        twiss.setAlfx(2.128241);
        twiss.setDx(-0.110300);
        twiss.setDpx(0.03792);
        twiss.setBety(71.515031);
        twiss.setAlfy(-1.679855);
        twiss.setDy(0.0);
        twiss.setDpy(0.0);

        return twiss;
    }

    /**
     * The twiss for injection in LHC point 8 (for sequence ending at "TCLA.A7L7.B2"
     * 
     * @return
     */

    private final TwissInitialConditionsImpl createLhcB2InjTwiss() {
        TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl("lhcb2-twiss");

        twiss.setDeltap(0.0);
        twiss.setBetx(66.829881);
        twiss.setAlfx(-0.000009);
        twiss.setDx(0.107789);
        twiss.setDpx(0.003062);
        twiss.setBety(140.995239);
        twiss.setAlfy(-2.127759);
        twiss.setDy(0.000136);
        twiss.setDpy(0.000003);

        return twiss;
    }

    private final Beam createBeam(Beam.Direction direction) {
        Beam beam = BeamFactory.createDefaultLhcBeam();
        beam.setDirection(direction);
        return beam;
    }

    //
    // @Override
    // protected ApertureDefinition createB1ApertureDefinition() {
    // ApertureDefinitionImpl aperture = new ApertureDefinitionImpl(new CallableModelFileImpl(
    // "aperture/APERIDX.LHC.B1.tfs", ModelFileLocation.RESOURCE));
    //
    // aperture.addPartFile(new CallableModelFileImpl("aperture/twiss.ir1_prof.b1.n1_inj.tfs.bz2"));
    //
    // return aperture;
    //
    // }
}
