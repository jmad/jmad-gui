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
import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;

/**
 * Represents a general service to store java objects into xml files. It uses the {@link XStream} mechanism to
 * accomplish this task.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 * @param <T> the type of the object which can be saved and loaded
 */
public interface PersistenceService<T> {

    /**
     * Write an object to the given xmlFile, the object/class must provide the appropriate annotations for writing the
     * XML...
     * 
     * @param object Object to write to XML
     * @param file the file to write to...
     * @return the file where it was saved in the end ..
     * @throws PersistenceServiceException if the serializing fails
     */
    public abstract File save(T object, File file) throws PersistenceServiceException;

    /**
     * Write an object as xml to the given output stream.
     * 
     * @param object the object to write
     * @param outStream the output stream to write to
     * @throws PersistenceServiceException if something fails
     */
    public abstract void save(T object, OutputStream outStream) throws PersistenceServiceException;

    /**
     * Creates an object from the given xmlFile
     * 
     * @param file the xmlFile to load the object from
     * @return the object created from XML or null in case of failure
     * @throws PersistenceServiceException if the loading of the object fails
     */
    public abstract T load(File file) throws PersistenceServiceException;

    /**
     * loads a new object from an input stream.
     * 
     * @param inputStream the input stream from which to read
     * @return the new object
     * @throws PersistenceServiceException if the loading of the object fails
     */
    public abstract T load(InputStream inputStream) throws PersistenceServiceException;

    /**
     * Creates a new Instance of the provided {@link Object} by creating a XML-String of the Object Data and
     * instantiating a new Object from that String
     * 
     * @param object the {@link Object} you want to get a clone from
     * @return the cloned Object
     * @throws PersistenceServiceException if the cloning of the object fails
     */
    public abstract T clone(T object) throws PersistenceServiceException;

    /**
     * @return the file extension which is used to load/save files
     */
    public String getFileExtension();

	boolean isCorrectFileName(String fileName);

}
