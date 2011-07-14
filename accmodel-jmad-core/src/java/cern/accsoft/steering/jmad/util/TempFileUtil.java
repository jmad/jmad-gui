// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
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
