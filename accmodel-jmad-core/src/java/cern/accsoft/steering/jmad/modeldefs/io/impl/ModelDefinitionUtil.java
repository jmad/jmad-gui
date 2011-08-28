// @formatter:off
/*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cern.accsoft.steering.jmad.JMadConstants;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFileDependant;
import cern.accsoft.steering.jmad.modeldefs.ClassPathModelDefinitionFinder;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This class contains some utility methods for model definitions
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public final class ModelDefinitionUtil {

	/**
	 * this is the class relative to which the model-definitions and files will
	 * be searched.
	 */
	public static final Class<?> BASE_CLASS = ClassPathModelDefinitionFinder.class;

	/**
	 * the package name relative to the base class where to search for
	 * model-definitions and files
	 */
	public static final String PACKAGE_OFFSET = "defs";

	/** the default file extension for xml files */
	public static final String XML_FILE_EXTENSION = ".jmd.xml";

	/** the default file extension for json files */
	public static final String JSON_FILE_EXTENSION = ".jmd.json";

	/** The extension used for jmad zip files */
	public static final String ZIP_FILE_EXTENSION = ".jmd.zip";

	/**
	 * the private constructor to prevent instantiation
	 */
	private ModelDefinitionUtil() {
		/* Only static methods */
	}

	public static String getProposedDefaultFileName(
			JMadModelDefinition modelDefinition) {
		return getProposedIdStringFromName(modelDefinition)
				+ getDefaultFileExtension();
	}

	public static String getDefaultFileExtension() {
		return XML_FILE_EXTENSION;
	}

	/**
	 * creates a file name for the given model definition
	 * 
	 * @param modelDefinition
	 *            the model definition for which to create the filename
	 * @return the filename
	 */
	public static String getProposedXmlFileName(
			JMadModelDefinition modelDefinition) {
		return getProposedIdStringFromName(modelDefinition)
				+ XML_FILE_EXTENSION;
	}

	/**
	 * creates a json-filename for the given model definition
	 * 
	 * @param modelDefinition
	 *            the model definition for which to create the filename
	 * @return the filename
	 */
	public static String getProposedJsonFileName(
			JMadModelDefinition modelDefinition) {
		return getProposedIdStringFromName(modelDefinition)
				+ JSON_FILE_EXTENSION;
	}

	/**
	 * creates an Id for the given model definition from its name. This is done
	 * by replacing all '(' and ')' by empty strings and all spaces by '-'.
	 * Note: Neither uniquenes is guaranteed nor that it will be a valid
	 * filename for example.
	 * 
	 * @param modelDefinition
	 *            the model definition for which to create the id
	 * @return the proposed Id as string
	 */
	public static String getProposedIdStringFromName(
			JMadModelDefinition modelDefinition) {
		return modelDefinition.getName().replaceAll("\\(", "")
				.replaceAll("\\)", "").toLowerCase().replaceAll(" ", "-");
	}

	/**
	 * determines if the given name is a model definition xml file
	 * 
	 * @param fileName
	 *            the file name to check
	 * @return true if it is an xml file name, false if not
	 */
	public static boolean isXmlFileName(String fileName) {
		return fileName.toLowerCase().endsWith(XML_FILE_EXTENSION);
	}

	/**
	 * adds the correct file extension for jmad zip files if necessary
	 * 
	 * @param file
	 *            the file which shall be a correct jmad zip file
	 * @return the corrected file
	 */
	public static File ensureZipFileExtension(File file) {
		if (isZipFileName(file.getName())) {
			return file;
		} else {
			return new File(file.getAbsolutePath() + ZIP_FILE_EXTENSION);
		}
	}

	/**
	 * determines if the given name is a model definition zip file
	 * 
	 * @param fileName
	 *            the file name to check
	 * @return true if it is a jmad zip file name, false if not
	 */
	public static boolean isZipFileName(String fileName) {
		return fileName.toLowerCase(JMadConstants.DEFAULT_LOCALE).endsWith(
				ZIP_FILE_EXTENSION);
	}

	/**
	 * collects all the model files which are required by a model definition and
	 * its parts
	 * 
	 * @param modelDefinition
	 *            the {@link JMadModelDefinition} for which to collect the model
	 *            files
	 * @return all the required model files
	 */
	public static final Collection<ModelFile> getRequiredModelFiles(
			JMadModelDefinition modelDefinition) {

		Set<ModelFile> requiredFiles = new HashSet<ModelFile>();
		for (ModelFileDependant modelFileDependant : getModelFileDependants(modelDefinition)) {
			requiredFiles.addAll(modelFileDependant.getRequiredFiles());
		}
		return requiredFiles;
	}

	/**
	 * collects all the parts of a model definition that potentially depend on
	 * model files
	 * 
	 * @param modelDefinition
	 *            the model definition for which to collect the dependent
	 *            classes
	 * @return all the model-file dependent classes
	 */
	private static final Collection<ModelFileDependant> getModelFileDependants(
			JMadModelDefinition modelDefinition) {
		List<ModelFileDependant> modelFileDependants = new ArrayList<ModelFileDependant>();

		/*
		 * XXX: not very nice. Here all parts of a model definition which
		 * potentially depend on model files are added manually to the
		 * collection.
		 */
		modelFileDependants.add(modelDefinition);
		modelFileDependants.addAll(modelDefinition.getRangeDefinitions());
		modelFileDependants.addAll(modelDefinition.getOpticsDefinitions());
		return modelFileDependants;
	}
}
