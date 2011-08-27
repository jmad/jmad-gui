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
 * 
 */
public class Ti809ModelDefinitionFactory implements ModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("TI8 (2009)");

		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setResourceOffset("ti8/ti8-2009");
		modelDefinition.setModelPathOffsets(offsets);

		modelDefinition.addInitFile(new CallableModelFileImpl("TI8.str",
				ModelFileLocation.RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("b2b3.str",
				ModelFileLocation.RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("TI8.seq",
				ModelFileLocation.RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("setb2b3.madx",
				ModelFileLocation.RESOURCE));

		OpticsDefinition opticsDefinition = new OpticsDefinitionImpl(
				"default optics",
				new ModelFile[] {
						new CallableModelFileImpl("TI8.str",
								ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
						new CallableModelFileImpl("b2b3.str",
								ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
		modelDefinition.setDefaultOpticsDefinition(opticsDefinition);

		/*
		 * SEQUENCE
		 */

		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl ti8 = new SequenceDefinitionImpl("ti8",
				BeamFactory.createDefaultLhcBeam());
		modelDefinition.setDefaultSequenceDefinition(ti8);

		RangeDefinitionImpl range = new RangeDefinitionImpl(ti8, "ALL",
				createTi8Twiss());
//		range.addResponeElementNameRegexp("BPK.*");
//		range.addResponeElementNameRegexp("BPM.*");
//		range.addResponeElementNameRegexp("BPT.*");
//		range.addResponeElementNameRegexp("BTV.*");
		ti8.setDefaultRangeDefinition(range);

		return modelDefinition;

	}

	/*
	 * values from malika !betx = 17.06248574,alfx =0.4588913839,
	 * !bety=124.8219205, alfy=-3.444554843, !dx= -0.2527900995,dpx=
	 * 0.00334144588; ///model May 2008
	 */
	private final TwissInitialConditionsImpl createTi8Twiss() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"ti8-twiss");

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
