/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.sps;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;

/**
 * This class is the implementation of the model definition for the Sps
 * 
 * @author kfuchsbe
 * 
 */
public class SpsModelDefinitionFactory implements ModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("SPS");

		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setRepositoryOffset("sps/optics2009/SPS/SPSRing/2009");
		offsets.setResourceOffset("sps");
		modelDefinition.setModelPathOffsets(offsets);

		/*
		 * the length of a rectangular magnet is the distance between the
		 * polefaces and not the arc length
		 */
		modelDefinition.addInitFile(new CallableModelFileImpl("init-options.madx",
				ModelFileLocation.RESOURCE));

		/* beam */
		modelDefinition.addInitFile(new CallableModelFileImpl(
				"beams/LHC_beam_injection.beamx"));

		/* elements */
		modelDefinition.addInitFile(new CallableModelFileImpl("elements/sps2009.ele"));

		/*
		 * call the aperture definition file for SPS. These are groups of
		 * elements
		 */
		modelDefinition.addInitFile(new CallableModelFileImpl(
				"aperture/aperturedb_1_2009.dbx"));

		/*
		 * aperture of QF1, QF2 and QD
		 */
		modelDefinition.addInitFile(new CallableModelFileImpl(
				"aperture/aperturedb_2_2009.dbx"));

		/*
		 * call the sequence definition file for SPS
		 */
		modelDefinition.addInitFile(new CallableModelFileImpl("sequence/sps2009.seq"));

		/*
		 * aperture of individual elements
		 */
		modelDefinition.addInitFile(new CallableModelFileImpl(
				"aperture/aperturedb_3_2009.dbx"));

		/*
		 * call the two strength definition files for SPS:.str & elements.str
		 */
		modelDefinition.addInitFile(new CallableModelFileImpl(
				"strength/lhc_newwp_2009.str"));
		modelDefinition.addInitFile(new CallableModelFileImpl("strength/elements.str"));

		/*
		 * OPTICS
		 */
		OpticsDefinition opticsDefinition = new OpticsDefinitionImpl(
				"default optics", new CallableModelFileImpl(
						"strength/lhc_newwp_2009.str",
						ModelFileLocation.REPOSITORY, ParseType.STRENGTHS));
		modelDefinition.setDefaultOpticsDefinition(opticsDefinition);

		/*
		 * SEQUENCE
		 */
		SequenceDefinitionImpl sps = new SequenceDefinitionImpl("sps", null);
		modelDefinition.setDefaultSequenceDefinition(sps);

		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"default-twiss");
		twiss.setCalcChromaticFunctions(true);
		sps
				.setDefaultRangeDefinition(new RangeDefinitionImpl(sps, "ALL",
						twiss));

		return modelDefinition;
	}
}
