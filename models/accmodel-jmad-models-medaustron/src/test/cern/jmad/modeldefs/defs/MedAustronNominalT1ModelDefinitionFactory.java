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
 * the model definition for med-austron T1 mouse treatment line
 * 
 * @author FFA and SNO
 */
public class MedAustronNominalT1ModelDefinitionFactory implements ModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("MedAustron T1 (nominal)");

		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setResourceOffset("ma");
		modelDefinition.setModelPathOffsets(offsets);

		modelDefinition.addInitFile(new CallableModelFileImpl("common/elements.def", RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("t1/t1.def", RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("t1/t1.seq", RESOURCE));

		/*
		 * OPTICS
		 */
		OpticsDefinition opticsRf1 = new OpticsDefinitionImpl("nominal",
				new ModelFile[] { new CallableModelFileImpl("t1/t1-rf1.str", RESOURCE, STRENGTHS),
						new CallableModelFileImpl("t1/t1-corrector.str", RESOURCE, STRENGTHS) });
		modelDefinition.addOpticsDefinition(opticsRf1);
		modelDefinition.setDefaultOpticsDefinition(opticsRf1);

		OpticsDefinition opticsRf2 = new OpticsDefinitionImpl("RF2",
				new ModelFile[] { new CallableModelFileImpl("t1/t1-rf2.str", RESOURCE, STRENGTHS),
						new CallableModelFileImpl("t1/t1-corrector.str", RESOURCE, STRENGTHS) });
		modelDefinition.addOpticsDefinition(opticsRf2);

		/*
		 * SEQUENCE
		 */
		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl t1 = new SequenceDefinitionImpl("t1", new Beam());
		modelDefinition.setDefaultSequenceDefinition(t1);

		RangeDefinitionImpl all = new RangeDefinitionImpl(t1, "ALL", createInititalConditions());
		t1.setDefaultRangeDefinition(all);

		return modelDefinition;
	}

	/**
	 * Creates the initial conditions as found in the reference model, (2016-05-19):
	 * default initial HEBT (T1) twiss parameters
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
