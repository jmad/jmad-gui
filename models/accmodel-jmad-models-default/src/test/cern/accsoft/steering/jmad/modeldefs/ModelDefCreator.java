package cern.accsoft.steering.jmad.modeldefs;

import java.io.File;

import org.apache.log4j.BasicConfigurator;

import cern.accsoft.steering.jmad.modeldefs.defs.lhc.LhcB4ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.lhc.LhcModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti2.LongTi208ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti2.LongTi2ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi808After0823ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi808Before0823ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi809ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi8ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.sps.SpsModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.spsextract.TT40SpsExtractionModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti2.Ti209ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti2.Ti2ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti808After0823ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti808Before0823ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti809ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti8ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.tt66.Tt66ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.modeldefs.io.impl.JsonModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;
import cern.accsoft.steering.jmad.modeldefs.io.impl.XmlModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.xml.PersistenceServiceException;

public class ModelDefCreator {

	private String destPath = null;

	private Mode mode = Mode.JSON;

	private static enum Mode {
		XML, JSON;
	}

	public ModelDefCreator(String destPath) {
		this.destPath = destPath;
	}

	public void createThem() {

		ModelDefinitionFactory[] factories = new ModelDefinitionFactory[] {
				new LhcB4ModelDefinitionFactory(), //
				new LhcModelDefinitionFactory(),//
				new LongTi208ModelDefinitionFactory(), //
				new LongTi2ModelDefinitionFactory(), //
				new LongTi808Before0823ModelDefinitionFactory(), //
				new LongTi808After0823ModelDefinitionFactory(), //
				new LongTi809ModelDefinitionFactory(), //
				new LongTi8ModelDefinitionFactory(), //
				new SpsModelDefinitionFactory(), //
				new TT40SpsExtractionModelDefinitionFactory(), //
				new Ti209ModelDefinitionFactory(), //
				new Ti2ModelDefinitionFactory(), //
				new Ti808Before0823ModelDefinitionFactory(), //
				new Ti808After0823ModelDefinitionFactory(), //
				new Ti809ModelDefinitionFactory(), //
				new Ti8ModelDefinitionFactory(), //
				new Tt66ModelDefinitionFactory() };

		for (ModelDefinitionFactory factory : factories) {
			JMadModelDefinition modelDefinition = factory.create();
			File file = getFile(modelDefinition);
			if (mode == Mode.XML) {
				writeToXml(modelDefinition, file);
			} else {
				writeToJson(modelDefinition, file);
			}

		}
	}

	private File getFile(JMadModelDefinition modelDefinition) {
		String fileName;
		if (mode == Mode.XML) {
			fileName = ModelDefinitionUtil
					.getProposedXmlFileName(modelDefinition);
		} else {
			fileName = ModelDefinitionUtil
					.getProposedJsonFileName(modelDefinition);
		}
		String filePath;
		if (destPath != null) {
			filePath = destPath + "/" + fileName;
		} else {
			filePath = fileName;
		}
		File file = new File(filePath);
		System.out.println("Writing file '" + file.getAbsolutePath() + "'.");
		return file;
	}

	private void writeToXml(JMadModelDefinition modelDefinition, File file) {
		ModelDefinitionPersistenceService service = new XmlModelDefinitionPersistenceService();
		try {
			service.save(modelDefinition, file);
		} catch (PersistenceServiceException e) {
			System.out.println("Could not save model definition to file '"
					+ file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	private void writeToJson(JMadModelDefinition modelDefinition, File file) {

		ModelDefinitionPersistenceService service = new JsonModelDefinitionPersistenceService();
		try {
			service.save(modelDefinition, file);
		} catch (PersistenceServiceException e) {
			System.out.println("Could not save model definition to file '"
					+ file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	public final static void main(String[] args) {
		BasicConfigurator.configure();

		ModelDefCreator creator = null;
		if (args.length > 0) {
			creator = new ModelDefCreator(args[0]);
		} else {
			creator = new ModelDefCreator(null);
		}

		creator.createThem();

	}

}
