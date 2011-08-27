/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.ti2;

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
 * 
 */
public class Ti209ModelDefinitionFactory implements ModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("TI2 (2009)");

		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setResourceOffset("ti2/ti2-2009");
		modelDefinition.setModelPathOffsets(offsets);

		modelDefinition.addInitFile(new CallableModelFileImpl("ti2.seq",
				ModelFileLocation.RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("setb2b3.madx",
				ModelFileLocation.RESOURCE));

		OpticsDefinition opticsDefinitions = new OpticsDefinitionImpl(
				"default optics",
				new ModelFile[] {
						new CallableModelFileImpl("ti2.str",
								ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
						new CallableModelFileImpl("b2b3.str",
								ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
		modelDefinition.setDefaultOpticsDefinition(opticsDefinitions);

		/*
		 * SEQUENCE
		 */

		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl ti2 = new SequenceDefinitionImpl("ti2",
				BeamFactory.createDefaultLhcBeam());
		modelDefinition.setDefaultSequenceDefinition(ti2);
		RangeDefinitionImpl ti2range = new RangeDefinitionImpl(ti2, "ALL",
				createTi2Twiss());
		ti2.setDefaultRangeDefinition(ti2range);

//		ti2range.addResponeElementNameRegexp("BPCK.*");
//		ti2range.addResponeElementNameRegexp("BPMI.*");
		/* XXX invert the X-bpms */
		ti2range.addCorrectorInvertFilter(new RegexNameFilter("(?i)^MDL.*",
				JMadPlane.H));
		ti2range.addCorrectorInvertFilter(new RegexNameFilter("(?i)^MDL.*",
				JMadPlane.V));

		return modelDefinition;
	}

	/**
	 * Twiss initial conditions for transferline Ti2
	 * 
	 */
	private final TwissInitialConditionsImpl createTi2Twiss() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"ti2-twiss");

		/*
		 * original values
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

		/*
		 * changes as proposed by malika and brennan
		 */
		// twiss.setX(0.0042);
		// twiss.setPx(-87E-6);
		// twiss.setY(-0.00056);
		// twiss.setPy(-0.47E-6);
		// twiss.setDy(-0.0022);
		// twiss.setDpy(60E-4);
		return twiss;

	}

}
