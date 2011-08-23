package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.beam.Beam.Direction;
import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.filter.RegexNameFilter;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.factory.BeamFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;

/**
 * The model definition for the LHC model
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 * 
 */
public class LhcModelDefinitionFactory extends
		AbstractLhcModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("lhc");

		modelDefinition.setModelPathOffsets(createModelPathOffsets());

		modelDefinition.addInitFile(new CallableModelFileImpl("V6.5.seq"));
		modelDefinition.addInitFile(new CallableModelFileImpl("setb2b3-lhc-b1.madx",
				ModelFileLocation.RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("setb2b3-lhc-b2.madx",
				ModelFileLocation.RESOURCE));

		List<OpticsDefinition> opticsDefinitions = createOpticsDefinitions();
		for (OpticsDefinition opticsDefinition : opticsDefinitions) {
			modelDefinition.addOpticsDefinition(opticsDefinition);
		}
		modelDefinition.setDefaultOpticsDefinition(opticsDefinitions.get(0));

		/*
		 * definition for beam 1
		 */

		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl lhcb1 = new SequenceDefinitionImpl("lhcb1",
				BeamFactory.createDefaultLhcBeam());
		modelDefinition.addSequenceDefinition(lhcb1);
		modelDefinition.setDefaultSequenceDefinition(lhcb1);

		RangeDefinition allRange = createB1Range(lhcb1, "ALL", "#s", "#e",
				createDefaultTwiss());
		lhcb1.addRangeDefinition(allRange);
		lhcb1.setDefaultRangeDefinition(allRange);

		lhcb1.addRangeDefinition(createB1Range(lhcb1, "ALL first turn", "#s",
				"#e", createLhcB1InjTwiss()));
		lhcb1.addRangeDefinition(createB1Range(lhcb1, "Inj. Sector 2-3",
				"LHCINJ.B1", "TCLA.7R3.B1", createLhcB1InjTwiss()));
		lhcb1.addRangeDefinition(createB1Range(lhcb1, "Inj. Sector 2-5",
				"LHCINJ.B1", "IP5", createLhcB1InjTwiss()));

		/*
		 * definition for beam 2
		 */

		Beam beam2 = BeamFactory.createDefaultLhcBeam();
		beam2.setDirection(Direction.MINUS);

		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl lhcb2 = new SequenceDefinitionImpl("lhcb2",
				beam2);
		modelDefinition.addSequenceDefinition(lhcb2);

		RangeDefinition allRangeB2 = createB2Range(lhcb2, "ALL", "#s", "#e",
				createDefaultTwiss());
		lhcb2.addRangeDefinition(allRangeB2);
		lhcb2.setDefaultRangeDefinition(allRangeB2);

		lhcb2.addRangeDefinition(createB2Range(lhcb2, "ALL first turn", "#s",
				"#e", createLhcB2InjTwiss()));
		lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-7",
				"TCLA.A7L7.B2", "LHCINJ.B2", createLhcB2InjTwiss()));
		lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-6",
				"TCDQA.A4L6.B2", "LHCINJ.B2", createLhcB2InjTwiss()));
		lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-5",
				"IP5", "LHCINJ.B2", createLhcB2InjTwiss()));
		lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-4",
				"IP4", "LHCINJ.B2", createLhcB2InjTwiss()));
		lhcb2.addRangeDefinition(createB2Range(lhcb2, "Inj. Sectors 8-1",
				"IP1", "LHCINJ.B2", createLhcB2InjTwiss()));

		return modelDefinition;
	}

	private final RangeDefinitionImpl createB1Range(
			SequenceDefinition sequenceDefinition, String name, String start,
			String end, TwissInitialConditionsImpl twiss) {
		RangeDefinitionImpl rangeDefinition = new RangeDefinitionImpl(
				sequenceDefinition, name, new MadxRange(start, end), twiss);
		// rangeDefinition.addResponeElementNameRegexp("BPM.*");
		rangeDefinition.setApertureDefinition(createB1ApertureDefinition());
		addPostUseFiles(rangeDefinition);
		return rangeDefinition;
	}

	private final RangeDefinitionImpl createB2Range(
			SequenceDefinition sequenceDefinition, String name, String start,
			String end, TwissInitialConditionsImpl twiss) {
		RangeDefinitionImpl rangeDefinition = new RangeDefinitionImpl(
				sequenceDefinition, name, new MadxRange(start, end), twiss);
		// rangeDefinition.setBeamNumber(BeamNumber.BEAM_2);
		// rangeDefinition.addResponeElementNameRegexp("BPM.*");
		/* invert all MCBXes (?i) lets it ignore the case */
		rangeDefinition.addCorrectorInvertFilter(new RegexNameFilter(
				"(?i)^MCBX.*", JMadPlane.H));
		rangeDefinition.addCorrectorInvertFilter(new RegexNameFilter(
				"(?i)^MCBX.*", JMadPlane.V));
		addPostUseFiles(rangeDefinition);
		return rangeDefinition;
	}

	private final TwissInitialConditionsImpl createDefaultTwiss() {
		return new TwissInitialConditionsImpl("default-twiss");
	}

	/**
	 * the twiss for LHC injection in point 2
	 * 
	 * @return
	 */
	private final TwissInitialConditionsImpl createLhcB1InjTwiss() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"lhcb1-twiss");

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
	 * The twiss for injection in LHC point 8 (for sequence ending at
	 * "TCLA.A7L7.B2"
	 * 
	 * @return
	 */

	private final TwissInitialConditionsImpl createLhcB2InjTwiss() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"lhcb2-twiss");

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

}
