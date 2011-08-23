/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.spsextract;

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
 * This class is the actual model configuration for the TI2 transfer line.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 * 
 */
public class TT40SpsExtractionModelDefinitionFactory implements ModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("SPS-extraction (TT40)");

		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setResourceOffset("spsextract");
		modelDefinition.setModelPathOffsets(offsets);

		modelDefinition.addInitFile(new CallableModelFileImpl(
				"SPS_extraction_TT40.seq", ModelFileLocation.RESOURCE));

		OpticsDefinition opticsDefinition = new OpticsDefinitionImpl(
				"default optics",
				new ModelFile[] { new CallableModelFileImpl("SPS_extraction_TT40.str",
						ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
		modelDefinition.setDefaultOpticsDefinition(opticsDefinition);

		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl spsextra = new SequenceDefinitionImpl(
				"spsextra", BeamFactory.createDefaultLhcBeam());
		modelDefinition.setDefaultSequenceDefinition(spsextra);
		RangeDefinitionImpl range = new RangeDefinitionImpl(spsextra, "ALL",
				createTwiss());
		spsextra.setDefaultRangeDefinition(range);

//		range.addResponeElementNameRegexp("BP.*");

		return modelDefinition;
	}

	/**
	 * Twiss for SPS extraction
	 */
	private final TwissInitialConditionsImpl createTwiss() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"ti2-twiss");

		twiss.setDeltap(0.0);

		twiss.setBetx(94.19052473);
		twiss.setBety(23.37564376);
		twiss.setMux(14.75568751);
		twiss.setMuy(14.77184545);
		twiss.setDx(3.406882672);
		twiss.setDy(0.0);
		twiss.setAlfx(-2.188943304);
		twiss.setAlfy(0.663770153);
		twiss.setDdx(-5.866518207);
		twiss.setDdy(0.0);
		twiss.setDdpx(-0.1013887);
		twiss.setDdpy(0.0);
		twiss.setDpx(0.062160128);
		twiss.setDpy(0.0);

		twiss.setCalcChromaticFunctions(true);
		return twiss;

	}
}
