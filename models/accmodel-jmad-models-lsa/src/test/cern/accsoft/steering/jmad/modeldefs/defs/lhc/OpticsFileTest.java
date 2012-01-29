/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.defs.lhc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import test.LabelledParameterized;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.JMadModelDefinitionManager;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.modeldefs.lhc.LhcUtil;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;

/**
 * Test if all lsa model optics are matched correctly in tune and chroma. For now we only check the 2011 optics for the
 * FULL lhc ranges (lhcb1, lhcb2 ALL)
 * 
 * @author muellerg
 */
@RunWith(value = LabelledParameterized.class)
public class OpticsFileTest {
    private static final Logger LOGGER = Logger.getLogger(OpticsFileTest.class);

    private static JMadService SERVICE;

    static {
        BasicConfigurator.configure();
        SERVICE = JMadServiceFactory.createJMadService();

        LOGGER.info("Service created...");
    }

    @Parameters
    public static final Collection<Object> getParamters() {
        List<Object> parameters = new ArrayList<Object>();

        JMadModelDefinitionManager modelDefinitionManager = SERVICE.getModelDefinitionManager();
        JMadModelDefinition modelDefinition = modelDefinitionManager
                .getModelDefinition(LhcUtil.ATS_MODEL_DEFINITION_NAME);
        LOGGER.info("Number of ATS optics: " + modelDefinition.getOpticsDefinitions().size());

        parameters.add(new Object[] { "Checking ModelDefinition: " + modelDefinition.getName(), modelDefinition });

        modelDefinition = modelDefinitionManager.getModelDefinition(LhcUtil.NOMINAL_MODEL_DEFINITION_NAME);
        LOGGER.info("Number of nominal optics: " + modelDefinition.getOpticsDefinitions().size());
        parameters.add(new Object[] { "Checking ModelDefinition: " + modelDefinition.getName(), modelDefinition });

        modelDefinition = modelDefinitionManager.getModelDefinition(LhcUtil.THIN_MODEL_DEFINITION_NAME);
        LOGGER.info("Number of nominal optics: " + modelDefinition.getOpticsDefinitions().size());
        parameters.add(new Object[] { "Checking ModelDefinition: " + modelDefinition.getName(), modelDefinition });

        LOGGER.info("Test parameters created...");

        return parameters;
    }

    /** the range definition to test */
    private static JMadModel MODEL = null;
    private final JMadModelDefinition modelDefinition;

    /**
     * the constructor of the test
     * 
     * @throws JMadModelException
     */
    public OpticsFileTest(String testName, JMadModelDefinition modelDefinition) {
        this.modelDefinition = modelDefinition;

        LOGGER.info("***********************************  START EXECUTION OF: " + testName);
        MODEL = SERVICE.createModel(modelDefinition);
        LOGGER.info("Model created...");
    }

    @Test
    public void testInitFilesExist() {
        for (ModelFile modelFile : this.modelDefinition.getRequiredFiles()) {
            File file = MODEL.getModelFileFinder().getFile(modelFile);
            String message = "init file [" + modelFile.getName() + "] for definition ["
                    + this.modelDefinition.getName() + "] ";

            Assert.assertNotNull(message + "MUST NOT be NULL.", file);
            Assert.assertTrue(message + "MUST exist.", file.exists());
            Assert.assertTrue(message + "MUST be readable", file.canRead());
        }
    }

    @Test
    public void testRangeFilesExist() {
        for (RangeDefinition rangeDefinition : this.modelDefinition.getRangeDefinitions()) {
            for (ModelFile modelFile : rangeDefinition.getRequiredFiles()) {
                File file = MODEL.getModelFileFinder().getFile(modelFile);
                String message = "range file [" + modelFile.getName() + "] for definition ["
                        + this.modelDefinition.getName() + "] and range [" + rangeDefinition.getName() + "] ";

                Assert.assertNotNull(message + "MUST NOT be NULL.", file);
                Assert.assertTrue(message + "MUST exist.", file.exists());
                Assert.assertTrue(message + "MUST be readable", file.canRead());
            }
        }
    }

    @Test
    public void testOpticsFilesExist() {
        for (OpticsDefinition opticsDefinition : this.modelDefinition.getOpticsDefinitions()) {
            for (ModelFile modelFile : opticsDefinition.getRequiredFiles()) {
                File file = MODEL.getModelFileFinder().getFile(modelFile);
                String message = "optics file [" + modelFile.getName() + "] for definition ["
                        + this.modelDefinition.getName() + "] and optics [" + opticsDefinition.getName() + "] ";

                Assert.assertNotNull(message + "MUST NOT be NULL.", file);
                Assert.assertTrue(message + "MUST exist.", file.exists());
                Assert.assertTrue(message + "MUST be readable", file.canRead());
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            MODEL.cleanup();
            LOGGER.info("Teared-Down");
        } catch (JMadModelException e) {
            e.printStackTrace();
        }
    }
}
