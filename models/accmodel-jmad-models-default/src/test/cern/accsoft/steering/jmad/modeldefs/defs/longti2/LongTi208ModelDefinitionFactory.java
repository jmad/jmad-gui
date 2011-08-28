/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.defs.longti2;

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
 * The model definition for Ti2+LHC sector 23
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 * 
 */
public class LongTi208ModelDefinitionFactory implements ModelDefinitionFactory {

	@Override
	public JMadModelDefinition create() {
		JMadModelDefinitionImpl modelDefinition = new JMadModelDefinitionImpl();
		modelDefinition.setName("longti2 (2008)");

		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setResourceOffset("longti2/longti2-2008");
		modelDefinition.setModelPathOffsets(offsets);

		modelDefinition.addInitFile(new CallableModelFileImpl("ti2.seq",
				ModelFileLocation.RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl("V6.5.seq",
				ModelFileLocation.RESOURCE));
		modelDefinition.addInitFile(new CallableModelFileImpl(
				"job.ti2lhcV6.503.optics.madx", ModelFileLocation.RESOURCE));

		OpticsDefinition opticsDefinition = new OpticsDefinitionImpl(
				"default optics",
				new ModelFile[] {
						new CallableModelFileImpl("ti2.str",
								ModelFileLocation.RESOURCE, ParseType.STRENGTHS),
						new CallableModelFileImpl("V6.5.inj.str",
								ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });

		modelDefinition.setDefaultOpticsDefinition(opticsDefinition);

		/* NOTE: sequenceName must correspond to the name in .seq - file! */
		SequenceDefinitionImpl longti2 = new SequenceDefinitionImpl("ti2lhcb1",
				BeamFactory.createDefaultLhcBeam());
		modelDefinition.setDefaultSequenceDefinition(longti2);

		RangeDefinitionImpl b1range = new RangeDefinitionImpl(longti2, "ALL",
				createTi2Twiss());
		longti2.setDefaultRangeDefinition(b1range);
		//b1range.setBeamNumber(BeamNumber.BEAM_1);

		return modelDefinition;
	}

	/**
	 * Twiss for transferline Ti2 (original from loco - madx - files)
	 * 
	 * ptin = 0.0; betxin = 17.02748544; alfxin = 0.4583574683; dxin =
	 * -0.3408152943; dpxin = 0.01307653962; betyin = 123.9323528; alfyin =
	 * -3.422196857; dyin = 0; dpyin = 0;
	 * 
	 * twiss, ,pt=ptin,betx=betxin,alfx=alfxin,dx=dxin,dpx=dpxin,
	 * bety=betyin,alfy=alfyin,dy=dyin,dpy=dpyin, file="tmp/DISP";
	 */
	private final TwissInitialConditionsImpl createTi2Twiss() {
		TwissInitialConditionsImpl twiss = new TwissInitialConditionsImpl(
				"ti2-twiss");

		twiss.setDeltap(0.0);
		twiss.setBetx(17.02748544);
		twiss.setAlfx(0.4583574683);
		twiss.setDx(-0.3408152943);
		twiss.setDpx(0.01307653962);
		twiss.setBety(123.9323528);
		twiss.setAlfy(-3.422196857);
		twiss.setDy(0.0);
		twiss.setDpy(0.0);

		return twiss;

	}

}
