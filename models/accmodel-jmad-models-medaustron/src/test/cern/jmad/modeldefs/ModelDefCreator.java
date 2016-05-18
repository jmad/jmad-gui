package cern.jmad.modeldefs;

import java.io.File;

import org.apache.log4j.BasicConfigurator;

import cern.accsoft.steering.jmad.modeldefs.ModelDefinitionFactory;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.ModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;
import cern.accsoft.steering.jmad.modeldefs.io.impl.XmlModelDefinitionPersistenceService;
import cern.accsoft.steering.jmad.util.xml.PersistenceServiceException;
import cern.jmad.modeldefs.defs.MedAustronMainRingModelDefinitionFactory;
import cern.jmad.modeldefs.defs.MedAustronMebtVar3ModelDefinitionFactory;
import cern.jmad.modeldefs.defs.MedAustronNominalHebtModelDefinitionFactory;
import cern.jmad.modeldefs.defs.MedAustronNominalMebtModelDefinitionFactory;

public class ModelDefCreator {

    public final static void main(String[] args) {

        String destPath = "src/java/cern/accsoft/steering/jmad/modeldefs/defs";
        if (args.length > 0) {
            destPath = args[0];
        }

        BasicConfigurator.configure();

        ModelDefinitionFactory[] factories = new ModelDefinitionFactory[] {
                new MedAustronMainRingModelDefinitionFactory(), new MedAustronNominalMebtModelDefinitionFactory(),
                new MedAustronMebtVar3ModelDefinitionFactory(), new MedAustronNominalHebtModelDefinitionFactory() };

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
