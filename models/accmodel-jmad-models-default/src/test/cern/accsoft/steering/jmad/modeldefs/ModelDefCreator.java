package cern.accsoft.steering.jmad.modeldefs;

import java.io.File;

import org.apache.log4j.BasicConfigurator;

import cern.accsoft.steering.jmad.modeldefs.defs.lhc.LhcB4ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.lhc.LhcModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti2.LongTi2ModelDefinition08Factory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti2.LongTi2ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi8ModelDefinition08FactoryAfter0823;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi8ModelDefinition08FactoryBefore0823;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi8ModelDefinition09Factory;
import cern.accsoft.steering.jmad.modeldefs.defs.longti8.LongTi8ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.sps.SpsModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.spsextract.TT40SpsExtractionModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti2.Ti2ModelDefinition09Factory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti2.Ti2ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti8ModelDefinition08FactoryAfter0823;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti8ModelDefinition08FactoryBefore0823;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti8ModelDefinition09Factory;
import cern.accsoft.steering.jmad.modeldefs.defs.ti8.Ti8ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.defs.tt66.Tt66ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;
import cern.accsoft.steering.jmad.modeldefs.io.impl.XmlModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.xml.PersistenceServiceException;

public class ModelDefCreator {

    public final static void main(String[] args) {

        String destPath = "";
        if (args.length > 0) {
            destPath = args[0];
        }

        BasicConfigurator.configure();

        ModelDefinitionFactory[] factories = new ModelDefinitionFactory[] { new LhcB4ModelDefinitionFactory(), //
                new LhcModelDefinitionFactory(),//
                new LongTi2ModelDefinition08Factory(), //
                new LongTi2ModelDefinitionFactory(), //
                new LongTi8ModelDefinition08FactoryBefore0823(), //
                new LongTi8ModelDefinition08FactoryAfter0823(), //
                new LongTi8ModelDefinition09Factory(), //
                new LongTi8ModelDefinitionFactory(), //
                new SpsModelDefinitionFactory(), //
                new TT40SpsExtractionModelDefinitionFactory(), //
                new Ti2ModelDefinition09Factory(), //
                new Ti2ModelDefinitionFactory(), //
                new Ti8ModelDefinition08FactoryBefore0823(), //
                new Ti8ModelDefinition08FactoryAfter0823(), //
                new Ti8ModelDefinition09Factory(), //
                new Ti8ModelDefinitionFactory(), //
                new Tt66ModelDefinitionFactory() };

        ModelDefinitionPersistenceService service = new XmlModelDefinitionPersistenceService();

        for (ModelDefinitionFactory factory : factories) {
            JMadModelDefinition modelDefinition = factory.create();
            String fileName = ModelDefinitionUtil.getProposedXmlFileName(modelDefinition);
            String filePath;
            if (destPath.length() > 0) {
                filePath = destPath + "/" + fileName;
            } else {
                filePath = fileName;
            }
            File file = new File(filePath);
            System.out.println("Writing file '" + file.getAbsolutePath() + "'.");
            try {
                service.save(modelDefinition, file);
            } catch (PersistenceServiceException e) {
                System.out.println("Could not save model definition to file '" + file.getAbsolutePath());
                e.printStackTrace();
            }
        }

    }
}
