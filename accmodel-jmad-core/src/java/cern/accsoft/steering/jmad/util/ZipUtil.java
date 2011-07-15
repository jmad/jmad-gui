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
package cern.accsoft.steering.jmad.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * some utility methods for zip files
 * 
 * @author kfuchsbe
 */
public final class ZipUtil {

    /**
     * private constructor to prevent instantiation
     */
    private ZipUtil() {
        /* Only static methods */
    }

    /**
     * searches for all filenames that match the given regex-pattern
     * 
     * @param zipFile the zip file in which to search
     * @param pattern the pattern to search for
     * @return all the filenames that match the pattern
     */
    public static Collection<String> getFileNames(File zipFile, Pattern pattern) {
        ZipFile theZipFile;
        try {
            theZipFile = new ZipFile(zipFile);
        } catch (ZipException e) {
            throw new Error(e);
        } catch (IOException e) {
            throw new Error(e);
        }
        return getFileNames(theZipFile, pattern);
    }

    public static Collection<String> getFileNames(ZipFile zipFile, Pattern pattern) {
        ArrayList<String> retval = new ArrayList<String>();
        Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
        while (zipEntries.hasMoreElements()) {
            ZipEntry zipEntry = zipEntries.nextElement();
            String fileName = zipEntry.getName();
            boolean accept = pattern.matcher(fileName).matches();
            if (accept) {
                retval.add(fileName);
            }
        }

        try {
            zipFile.close();
        } catch (IOException e1) {
            throw new Error(e1);
        }
        return retval;
    }
}
