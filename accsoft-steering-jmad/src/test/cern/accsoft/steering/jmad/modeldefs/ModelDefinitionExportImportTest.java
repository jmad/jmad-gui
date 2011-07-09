package cern.accsoft.steering.jmad.modeldefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cern.accsoft.steering.jmad.JMadTestCase;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.domain.SourceInformation;
import cern.accsoft.steering.jmad.modeldefs.domain.SourceInformation.SourceType;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionExporter;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionImporter;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;
import cern.accsoft.steering.jmad.util.FileUtil;

public class ModelDefinitionExportImportTest extends JMadTestCase {

	private JMadModelDefinitionExporter exporter;
	private JMadModelDefinitionImporter importer;

	private final static File TEST_ZIP_FILE = new File("test.jmd.zip");

	private final static File TEST_DIR = new File("test");

	@Before
	public void setUp() throws Exception {
		exporter = getJMadService().getModelDefinitionExporter();
		importer = getJMadService().getModelDefinitionImporter();
	}

	@After
	public void tearDown() {
		if (!TEST_ZIP_FILE.delete()) {
			; /* Ignore, may not exist */
		}
		if (!FileUtil.deleteDir(TEST_DIR)) {
			; /* Ignore, may not exist */
		}
	}

	@Test
	public void testExportAsZip() {
		JMadModelDefinition modelDefinition = findExampleModelDefinition();
		if (!TEST_ZIP_FILE.delete()) {
			; /* Ignore, may not exist */
		}
		assertTrue("No test file must be available.", !TEST_ZIP_FILE.exists());
		File file = exporter.exportAsZip(modelDefinition, TEST_ZIP_FILE);
		assertNotNull("File must not be null if the export was successful.",
				file);
		assertTrue("Test file should exist", TEST_ZIP_FILE.exists());
		assertEquals("Return file should be the same as the original one.",
				TEST_ZIP_FILE.getAbsolutePath(), file.getAbsolutePath());

	}

	@Test
	public void testExportAsFilesWithExistingDir() {
		JMadModelDefinition modelDefinition = findExampleModelDefinition();
		if (!FileUtil.deleteDir(TEST_DIR)) {
			; /* Ignore, may not exist */
		}
		assertTrue("No test dir must be available.", !TEST_DIR.exists());
		FileUtil.createDir(TEST_DIR, false);
		File file = exporter.exportAsFiles(modelDefinition, TEST_DIR);
		assertNotNull("returned file must not be null", file);
		assertTrue("Test dir should exist", TEST_DIR.exists());
		assertEquals(
				"Returned parent dir should be the same as the original one.",
				TEST_DIR.getAbsolutePath(), file.getAbsoluteFile()
						.getParentFile().getAbsolutePath());
	}

	@Test
	public void testExportAsFilesWithFile() {
		JMadModelDefinition modelDefinition = findExampleModelDefinition();

		/*
		 * and once more with a file as call
		 */
		if (!FileUtil.deleteDir(TEST_DIR)) {
			; /* Ignore, may not exist */
		}
		assertTrue("No test dir must be available.", !TEST_DIR.exists());
		String exportFilePath = TEST_DIR.getAbsolutePath() + File.separator
				+ "test";
		File file = exporter.exportAsFiles(modelDefinition, new File(
				exportFilePath));
		assertNotNull("returned file must not be null", file);
		assertTrue("Test dir should exist", TEST_DIR.exists());
		assertTrue("export file should exist", file.exists());

		assertEquals(
				"Returned parent dir should be the same as the original one.",
				exportFilePath + ".jmd.xml", file.getAbsolutePath());
	}

	@Test
	public void testImportFromZip() throws JMadModelException {

		/* To test this we first have to export the model definition as zip */
		JMadModelDefinition modelDefinition = findExampleModelDefinition();
		if (!TEST_ZIP_FILE.delete()) {
			; /* Ignore, may not exist */
		}
		exporter.exportAsZip(modelDefinition, TEST_ZIP_FILE);

		/* and then reimport the model definitions from the zip file */
		Collection<JMadModelDefinition> modelDefinitions = importer
				.importModelDefinitions(TEST_ZIP_FILE);
		assertEquals("Zip file should contain exactly one model definition.",
				1, modelDefinitions.size());

		JMadModelDefinition importedDefinition = importer
				.importModelDefinition(TEST_ZIP_FILE);
		assertNotNull("Model definition must not be null.", importedDefinition);
		assertEquals(modelDefinition.getName(), importedDefinition.getName());

		/* The new definition should contain the new source information */
		SourceInformation sourceInformation = importedDefinition
				.getSourceInformation();
		assertEquals(TEST_ZIP_FILE.getAbsolutePath(), sourceInformation
				.getRootPath().getAbsolutePath());
		assertEquals(SourceType.ZIP, sourceInformation.getSourceType());
		assertEquals(ModelDefinitionUtil
				.getProposedXmlFileName(modelDefinition), sourceInformation
				.getXmlFileName());

		/*
		 * finally we try to create a model with the newly imported definition.
		 */
		JMadModel model = getJMadService().createModel(importedDefinition);
		model.init();
		model.twiss(new TfsResultRequestImpl());
		model.cleanup();
	}
}
