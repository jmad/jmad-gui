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

package cern.accsoft.steering.jmad.util.xml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

public abstract class GenericXStreamService<T> implements PersistenceService<T> {

	private static final Logger LOGGER = Logger
			.getLogger(GenericXStreamService.class);

	/** the original xstream xml-converter (singleton) */
	private final XStream xStream;

	public GenericXStreamService() {
		super();
		this.xStream = createXStream();
	}

	/**
	 * This method must be implemented by subclass and has to return a properly
	 * configured XStream object.
	 * 
	 * @return a fully configured xstream object
	 */
	protected abstract XStream createXStream();

	/**
	 * Has to return the class which can be saved by this service. This is used
	 * for checking and producing an error message, if an object cannot be
	 * saved.
	 * 
	 * @return The class which can be saved by this persistence service
	 */
	protected abstract Class<? extends T> getSaveableClass();

	@Override
	public synchronized final File save(T object, File destFile)
			throws PersistenceServiceException {

		if (!(getSaveableClass().isInstance(object))) {
			LOGGER.error("can only save model definitions of type '"
					+ getSaveableClass().getCanonicalName() + "' to file.");
			return null;
		}

		File file = ensureCorrectFileExtension(destFile);

		try {
			FileWriter writer = new FileWriter(file);
			writer.write(xStream.toXML(object));
			writer.close();
		} catch (Exception ex) {
			throw new PersistenceServiceException("Error writing Object ["
					+ object.toString() + "] of Class ["
					+ object.getClass().getCanonicalName() + "] to XmlFile ["
					+ file.getAbsolutePath() + "]", ex);
		}

		return file;
	}

	/**
	 * adds the correct extension for jmad-model files to the file name if not
	 * already there.
	 * 
	 * @param file
	 *            the file which shall be a correct jmad xml file
	 * @return the file
	 */
	private File ensureCorrectFileExtension(File file) {
		if (isCorrectFileName(file.getName())) {
			return file;
		} else {
			return new File(file.getAbsolutePath() + getFileExtension());
		}
	}

	/**
	 * determines if the given name is a correct file name
	 * 
	 * @param fileName
	 *            the file name to check
	 * @return true if it is an xml file name, false if not
	 */
	@Override
	public final boolean isCorrectFileName(String fileName) {
		return fileName.toLowerCase().endsWith(getFileExtension());
	}

	@Override
	public void save(T object, OutputStream outStream)
			throws PersistenceServiceException {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(outStream);
			writer.write(xStream.toXML(object));
			writer.flush();
		} catch (IOException e) {
			throw new PersistenceServiceException("Error writing Object ["
					+ object.toString() + "] of Class ["
					+ object.getClass().getCanonicalName()
					+ "] to output stream.", e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized T load(File srcFile) throws PersistenceServiceException {

		T retVal = null;
		try {
			retVal = (T) xStream.fromXML(new FileReader(srcFile));
		} catch (Exception ex) {
			throw new PersistenceServiceException(
					"Error loading Object from file ["
							+ srcFile.getAbsolutePath() + "]", ex);
		}

		return retVal;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized T load(InputStream inputStream)
			throws PersistenceServiceException {
		T retVal = null;
		try {
			retVal = (T) xStream.fromXML(new InputStreamReader(inputStream));
		} catch (Exception ex) {
			throw new PersistenceServiceException(
					"Error loading Object from Xml stream", ex);
		}
		return retVal;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized T clone(T object) throws PersistenceServiceException {

		T retVal = null;
		try {
			retVal = (T) xStream.fromXML(xStream.toXML(object));
		} catch (Exception ex) {

			String className = object.getClass().getCanonicalName();

			throw new PersistenceServiceException(
					"Error cloning Object of Class [" + className + "]", ex);
		}

		return retVal;
	}

}
