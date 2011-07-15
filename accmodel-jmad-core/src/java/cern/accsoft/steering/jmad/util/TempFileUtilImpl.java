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
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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
        outPath = outPath + File.separator + SystemUtil.getUserName() + File.separator + SystemUtil.getHostName();
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
