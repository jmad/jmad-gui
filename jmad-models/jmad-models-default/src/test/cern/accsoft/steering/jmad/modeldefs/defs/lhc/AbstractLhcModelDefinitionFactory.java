package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.domain.machine.ApertureDefinition;
import cern.accsoft.steering.jmad.domain.machine.ApertureDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinitionImpl;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelFileFinderImpl;
import cern.accsoft.steering.jmad.util.JMadPreferencesImpl;
import cern.accsoft.steering.jmad.util.TempFileUtilImpl;
import cern.accsoft.steering.util.io.TextFileParser;
import cern.accsoft.steering.util.io.TextFileParserException;
import cern.accsoft.steering.util.io.impl.TextFileParserImpl;

public abstract class AbstractLhcModelDefinitionFactory implements
		ModelDefinitionFactory {

	private final static Logger logger = Logger
			.getLogger(AbstractLhcModelDefinitionFactory.class);

	public AbstractLhcModelDefinitionFactory() {
		super();
	}

	protected ModelPathOffsets createModelPathOffsets() {
		ModelPathOffsetsImpl offsets = new ModelPathOffsetsImpl();
		offsets.setResourceOffset("lhc");
		offsets.setRepositoryOffset("lhc/optics/V6.503");
		return offsets;
	}

	protected List<OpticsDefinition> createOpticsDefinitions() {
		List<OpticsDefinition> opticsDefinitions = new ArrayList<OpticsDefinition>();

		/* the injection optics is the base for all overlays */
		OpticsDefinition baseOptics = new OpticsDefinitionImpl(
				"injection",
				new ModelFile[] {
						new CallableModelFileImpl("V6.5.inj.str",
								ModelFileLocation.REPOSITORY,
								ParseType.STRENGTHS),
						new CallableModelFileImpl("b2b3.str",
								ModelFileLocation.RESOURCE, ParseType.STRENGTHS) });
		opticsDefinitions.add(baseOptics);

		/* the collision optics is no overlay, but complete by its own. */
		opticsDefinitions.add(new OpticsDefinitionImpl("collision",
				new CallableModelFileImpl("V6.5.coll.str",
						ModelFileLocation.REPOSITORY, ParseType.STRENGTHS)));

		/*
		 * all the other optics we read from the filelist and consider as
		 * overlays to the injection optics.
		 */

		TempFileUtilImpl fileUtil = new TempFileUtilImpl();
		fileUtil.setPreferences(new JMadPreferencesImpl());
		ModelFileFinderImpl finder = new ModelFileFinderImpl();
		finder.setFileUtil(fileUtil);
		finder.setModelFilePathOffsets(createModelPathOffsets());

		File opticsListFile = finder.getFile(new CallableModelFileImpl(
				"optics.list", ModelFileLocation.RESOURCE));
		TextFileParser parser = new TextFileParserImpl();
		List<String> lines = new ArrayList<String>();
		try {
			lines = parser.parse(opticsListFile);
		} catch (TextFileParserException e) {
			logger.error("Unable to parse optics list for LHC model: '"
					+ opticsListFile.getAbsolutePath() + "'!", e);
			e.printStackTrace();
		}

		for (String line : lines) {
			String opticsName = line; // line.replaceAll(".*/([^/]*)\\.str.*",
			// "$1");
			String fileName = line;
			OpticsDefinitionImpl opticsDefinition = new OpticsDefinitionImpl(
					opticsName, true, new CallableModelFileImpl(fileName,
							ModelFileLocation.REPOSITORY, ParseType.STRENGTHS));
			opticsDefinitions.add(opticsDefinition);
		}

		return opticsDefinitions;
	}

	protected void addPostUseFiles(RangeDefinitionImpl rangeDefinition) {
		/*
		 * call, file="db5/measured_errors/Efcomp_MBRB.madx"; call,
		 * file="db5/measured_errors/Efcomp_MBRC.madx"; call,
		 * file="db5/measured_errors/Efcomp_MBRS.madx"; call,
		 * file="db5/measured_errors/Efcomp_MBX.madx" ; call,
		 * file="db5/measured_errors/Efcomp_MBW.madx" ; call,
		 * file="db5/measured_errors/Efcomp_MBXW.madx";
		 * on_B2S:=0;call,file="db5/measured_errors/Efcomp_MQ.madx"; on_B2S:=1;
		 * call, file="db5/measured_errors/Efcomp_MQM.madx" ; call,
		 * file="db5/measured_errors/Efcomp_MQMC.madx"; call,
		 * file="db5/measured_errors/Efcomp_MQML.madx"; call,
		 * file="db5/measured_errors/Efcomp_MQTL.madx"; call,
		 * file="db5/measured_errors/Efcomp_MQW.madx" ; on_B2S:=0;call,
		 * file="db5/measured_errors/Efcomp_MQX.madx"; on_B2S:=1; call,
		 * file="db5/measured_errors/Efcomp_MQY.madx" ;
		 */

		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MBRB.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MBRC.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MBRS.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MBX.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MBW.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MBXW.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQ.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQM.madx"));
		//
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQMC.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQML.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQTL.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQW.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQX.madx"));
		// rangeDefinition
		// .addPostUseFile(getRepositoryFile("measured_errors/Efcomp_MQY.madx"));
	}

	protected ApertureDefinition createB1ApertureDefinition() {
		ApertureDefinitionImpl aperture = new ApertureDefinitionImpl(
				new CallableModelFileImpl("aperture/APERIDX.LHC.B1.tfs",
						ModelFileLocation.RESOURCE));

		aperture.addPartFile(new CallableModelFileImpl(
				"aperture/twiss.ir1_prof.b1.n1_inj.tfs.bz2"));

		return aperture;

	}

}