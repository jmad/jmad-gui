/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.util.List;

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
 * The model definition for LHC beam 4
 * 
 * @author kfuchsbe
 * 
 */
public class LhcB4ModelDefinitionFactory extends
		AbstractLhcModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("lhc-beam4");
		
		modelDefinition.addInitFile(new CallableModelFileImpl("beam_four.seq"));
		modelDefinition.addInitFile(new CallableModelFileImpl("setb2b3-lhc-b2.madx",
				ModelFileLocation.RESOURCE));

		modelDefinition.setModelPathOffsets(createModelPathOffsets());

		List<OpticsDefinition> opticsDefinitions = createOpticsDefinitions();
		for (OpticsDefinition opticsDefinition : opticsDefinitions) {
			modelDefinition.addOpticsDefinition(opticsDefinition);
		}
		modelDefinition.setDefaultOpticsDefinition(opticsDefinitions.get(0));

		/*
		 * sequence definitions
		 */
		SequenceDefinitionImpl lhcb4 = new SequenceDefinitionImpl("lhcb2",
				BeamFactory.createDefaultLhcBeam());

		/*
		 * definition for beam 2
		 */
		RangeDefinition allRange = createB2Range(lhcb4, "ALL", "#s", "#e",
				new TwissInitialConditionsImpl("default twiss"));
		lhcb4.addRangeDefinition(allRange);
		lhcb4.setDefaultRangeDefinition(allRange);

		lhcb4.addRangeDefinition(createB2Range(lhcb4, "ALL first-turn", "#s",
				"#e", createLhcB4InjTwiss()));

		lhcb4.addRangeDefinition(createB2Range(lhcb4, "Inj. Sector 8-7",
				"LHCINJ.B2", "TCLA.A7L7.B2", createLhcB4InjTwiss()));
		lhcb4.addRangeDefinition(createB2Range(lhcb4, "Inj. Sector 8-6",
				"LHCINJ.B2", "TCDQA.A4L6.B2", createLhcB4InjTwiss()));
		lhcb4.addRangeDefinition(createB2Range(lhcb4, "Inj. Sectors 8-5",
				"LHCINJ.B2", "IP5", createLhcB4InjTwiss()));
		lhcb4.addRangeDefinition(createB2Range(lhcb4, "Inj. Sectors 8-4",
				"LHCINJ.B2", "IP4", createLhcB4InjTwiss()));
		lhcb4.addRangeDefinition(createB2Range(lhcb4, "Inj. Sectors 8-1",
				"LHCINJ.B2", "IP1", createLhcB4InjTwiss()));

		modelDefinition.addSequenceDefinition(lhcb4);
		modelDefinition.setDefaultSequenceDefinition(lhcb4);

		return modelDefinition;
	}

	private final RangeDefinitionImpl createB2Range(
			SequenceDefinition sequenceDefinition, String name, String start,
			String end, TwissInitialConditionsImpl twiss) {
		RangeDefinitionImpl rangeDefinition = new RangeDefinitionImpl(
				sequenceDefinition, name, new MadxRange(start, end), twiss);
//		rangeDefinition.setBeamNumber(BeamNumber.BEAM_2);

		/* invert all MCBXes (?i) lets it ignore the case */
		rangeDefinition.addCorrectorInvertFilter(new RegexNameFilter(
				"(?i)^MCBX.*", JMadPlane.H));
		rangeDefinition.addCorrectorInvertFilter(new RegexNameFilter(
				"(?i)^MCBX.*", JMadPlane.V));
		addPostUseFiles(rangeDefinition);
		return rangeDefinition;
	}

	private final TwissInitialConditionsImpl createLhcB4InjTwiss() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"lhcb4-twiss");

		twiss.setDeltap(0.0);
		twiss.setBetx(53.225011);
		twiss.setAlfx(1.728909);
		twiss.setDx(0.107789);
		twiss.setDpx(-0.003062);
		twiss.setBety(75.204871);
		twiss.setAlfy(-1.216716);
		twiss.setDy(0.000046);
		twiss.setDpy(0.000003);

		return twiss;
	}

}
