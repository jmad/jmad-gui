// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
