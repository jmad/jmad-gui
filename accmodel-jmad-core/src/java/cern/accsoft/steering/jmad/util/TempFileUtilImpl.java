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
/*
 * $Id: FileUtil.java,v 1.3 2008-09-09 23:41:42 kfuchsbe Exp $
 * 
 * $Date: 2008-09-09 23:41:42 $ $Revision: 1.3 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * This Class provides some methods to simply access output-files
 * 
 * @author kfuchsbe
 */
public class TempFileUtilImpl implements TempFileUtil {

    /** the name of the dir which is created below the basepath */
    public static final String TMPDIR_NAME = "jmad-tmp";

    /** the logger for the class */
    private static final Logger LOGGER = Logger.getLogger(TempFileUtilImpl.class);

    /** the output-path including username and host */
    private String outputPath = null;

    /** The preferences object, to be injected */
    private JMadPreferences preferences;

    /**
     * init - method called by spring
     */
    public void init() {
        this.outputPath = createDirTree();
    }

    @Override
    public final File getOutputFile(String relativePath) {
        return createFile(outputPath + File.separator + relativePath);
    }

    @Override
    public final File getOutputFile(Object object, String relativePath) {
        return createFile(getObjectPath(object) + File.separator + relativePath);
    }

    @Override
    public final void cleanup(Object object) {
        File dir = new File(getObjectPath(object));
        if (!FileUtil.deleteDir(dir)) {
            LOGGER.error("Could not delete directory '" + dir.getAbsolutePath() + "'");
        }
    }

    /**
     * @param object the object the temp-path belongs to
     * @return the path as string for the object
     */
    private final String getObjectPath(Object object) {
        return outputPath + File.separator + object.hashCode();
    }

    /**
     * creates a file object with the given path and takes care that all dirs up to the file exist.
     * 
     * @param fullPath the full path to the file
     * @return the file
     */
    private static final File createFile(String fullPath) {
        File file = new File(fullPath);

        /* create the parent dirs, if necessary */
        File parentDir = file.getAbsoluteFile().getParentFile();
        FileUtil.createDir(parentDir, false);

        return file;
    }

    /**
     * creates the output - dir tree with the correct user - rights
     * 
     * @return the final output - dir
     */
    private final String createDirTree() {
        JMadPreferences prefs = getPreferences();
        if (prefs == null) {
            return null;
        }
        String outPath = prefs.getOutputPath();

        /* the base dir must be writable by all users */
        outPath = outPath + File.separator + TMPDIR_NAME;
        FileUtil.createDir(new File(outPath), true);

        /* the sub dirs only have to be accessible by the user */
        outPath = outPath + File.separator + SystemUtil.getUserName() + File.separator
                + SystemUtil.getHostName();
        FileUtil.createDir(new File(outPath), false);

        return outPath;
    }

    public void setPreferences(JMadPreferences preferences) {
        this.preferences = preferences;
    }

    private JMadPreferences getPreferences() {
        if (this.preferences == null) {
            LOGGER.warn("Preferences not set. Maybe config error.");
        }
        return preferences;
    }

}
