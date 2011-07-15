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

package cern.accsoft.steering.jmad.util;

import java.io.File;

public interface TempFileUtil {

    /**
     * returns a file created from the current output-path and the given relative path. This method also ensures, that
     * the parent directory for the file is available.
     * 
     * @param relativePath the file-path in the current output-dir
     * @return the File
     */
    public abstract File getOutputFile(String relativePath);

    /**
     * returns a file created from the current output-path, a sub directory for the given object and the relative path.
     * This is especially useful, if multiple threads of jmad are running and the results shall not interfere.
     * 
     * @param object the object which is used to create a subdir name.
     * @param relativePath the relative path
     * @return the file
     */
    public abstract File getOutputFile(Object object, String relativePath);

    /**
     * recursively removes the dir related to the given object.
     * 
     * @param object the object for which to delete the dir
     */
    public abstract void cleanup(Object object);

}
