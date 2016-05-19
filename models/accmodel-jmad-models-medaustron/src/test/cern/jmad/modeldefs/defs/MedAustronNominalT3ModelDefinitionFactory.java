package cern.jmad.modeldefs.defs;

import static cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType.STRENGTHS;
import static cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation.RESOURCE;

import cern.accsoft.steering.jmad.domain.beam.Beam;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinitionImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;

/**
 * the model definition for med-austron T3 treatment line
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class MedAustronNominalT3ModelDefinitionFactory implements ModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("MedAustron T3 (nominal)");

		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setResourceOffset("ma");
		modelDefinition.setModelPathOffsets(offsets);

		modelDefinition.addInitFile(new CallableModelFileImpl("common/elements.def", RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("t3/t3.def", RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("t3/t3.seq", RESOURCE));

		/*
		 * OPTICS
		 */
		OpticsDefinition optics = new OpticsDefinitionImpl("nominal",
				new ModelFile[] { new CallableModelFileImpl("t3/t3.str", RESOURCE, STRENGTHS),
						new CallableModelFileImpl("t3/t3-corrector.str", RESOURCE, STRENGTHS) });
		modelDefinition.addOpticsDefinition(optics);
		modelDefinition.setDefaultOpticsDefinition(optics);

		/*
		 * SEQUENCE
		 */
		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl t3 = new SequenceDefinitionImpl("t3", new Beam());
		modelDefinition.setDefaultSequenceDefinition(t3);

		RangeDefinitionImpl all = new RangeDefinitionImpl(t3, "ALL", createInititalConditions());
		t3.setDefaultRangeDefinition(all);

		return modelDefinition;
	}

	/**
	 * Creates the initial conditions as given by A. Wastl, (2015-01-16):
	 * default initial HEBT (T3) twiss parameters in t3.matx, t3-twiss.madx,
	 * t3-bety2m-twiss.madx // CAVE: t3-bety2m remains with hardcoded 'bety =
	 * 2.0'
	 * <ul>
	 * <li>t3.betx = 3; // default: 3
	 * <li>t3.alfx = 0; // default: 0
	 * <li>t3.dx = 0; // default: 0
	 * <li>t3.dpx = 0; // default: 0
	 * <li>t3.bety = 3; // default: 3
	 * <li>t3.alfy = 0; // default: 0
	 * <li>t3.dy = 0; // default: 0
	 * <li>t3.dpy = 0; // default: 0
	 * </ul>
	 */
	protected TwissInitialConditionsImpl createInititalConditions() {
		TwissInitialConditionsImpl inititalConditions = new TwissInitialConditionsImpl();
		inititalConditions.setBetx(3.0);
		inititalConditions.setAlfx(0.0);
		inititalConditions.setDx(0.0);
		inititalConditions.setDpx(0.0);

		inititalConditions.setBety(3.0);
		inititalConditions.setAlfy(0.0);
		inititalConditions.setDy(0.0);
		inititalConditions.setDpy(0.0);
		return inititalConditions;
	}

}
