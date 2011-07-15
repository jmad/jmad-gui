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
 * $Id: MadXBin.java,v 1.6 2008-12-12 14:48:13 kfuchsbe Exp $
 * 
 * $Date: 2008-12-12 14:48:13 $ $Revision: 1.6 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.bin;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.util.OsUtil;
import cern.accsoft.steering.jmad.util.StreamUtil;
import cern.accsoft.steering.jmad.util.TempFileUtil;

/**
 * Determines the correct version of the madx-executable and provides methods to start its execution. Depending on the
 * operating system the correct executable is extracted to a temporary directory and can be executed from there.
 * 
 * @author kfuchsbe
 */
public class MadxBinImpl implements MadxBin {
    /** the logger */
    private static final Logger LOGGER = Logger.getLogger(MadxBinImpl.class);

    /**
     * default values
     */
    private static final String BIN_NAME_DEFAULT = "madx";

    /*
     * values used for windows
     */
    private static final String BIN_NAME_WIN = "madx.exe";
    private static final String RESOURCE_PREFIX_WIN = "win/";

    /*
     * values used for linux
     */
    private static final String BIN_NAME_LINUX = "madx";
    private static final String RESOURCE_PREFIX_LINUX = "linux/";

    /*
     * values used for mac osx
     */
    private static final String BIN_NAME_OSX = "madx";
    private static final String RESOURCE_PREFIX_OSX = "osx/";

    /** The file util to use (injected by spring) */
    private TempFileUtil fileUtil;

    /** The filename of the executable, which then can be called by a shell */
    private String executablePath;

    /**
     * init-method called by spring
     */
    public void init() {
        extractExecutable();
    }

    @Override
    public Process execute() throws IOException {
        return Runtime.getRuntime().exec(getExecutablePath());
    }

    /**
     * @return the path to the executable depending on the OS
     */
    private final String getExecutablePath() {
        return executablePath;
    }

    /**
     * @return the name of the executable
     */
    private static final String getExecutableName() {
        if (OsUtil.isWindows()) {
            return BIN_NAME_WIN;
        } else if (OsUtil.isLinux()) {
            return BIN_NAME_LINUX;
        } else if (OsUtil.isOsX()) {
            return BIN_NAME_OSX;
        } else {
            return BIN_NAME_DEFAULT;
        }
    }

    /**
     * @return the name of the resource depending on the OS
     */
    private static final String getResourceName() {
        if (OsUtil.isWindows()) {
            return RESOURCE_PREFIX_WIN + BIN_NAME_WIN;
        } else if (OsUtil.isLinux()) {
            return RESOURCE_PREFIX_LINUX + BIN_NAME_LINUX;
        } else if (OsUtil.isOsX()) {
            return RESOURCE_PREFIX_OSX + BIN_NAME_OSX;
        } else {
            LOGGER.warn("No madx binary available in jar for operating system '" + OsUtil.getOsName()
                    + "'.\n If you have no executable in the path,\n"
                    + "you will not be able to perform any calculations!");
            return null;
        }
    }

    /**
     * copies the executable to the actual path. This is necessary, when the file is included in a jar.
     */
    private final void extractExecutable() {
        if (getFileUtil() == null) {
            return;
        }
        LOGGER.debug("Extracting madx binary for further use.");

        String resourceName = getResourceName();
        if (resourceName == null) {
            /*
             * we just use the executable name. (executable has to be in the path.)
             */
            executablePath = getExecutableName();
        } else {
            /* unpack the executable */
            File file = getFileUtil().getOutputFile(getExecutableName());

            StreamUtil.toFile(MadxBinImpl.class.getResourceAsStream(resourceName), file);

            /* allow to execute the file (for the current user) */
            file.setExecutable(true);

            /* store the absolute path to be able to execute it */
            executablePath = file.getAbsolutePath();
        }
    }

    public void setFileUtil(TempFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    /**
     * @return the instance of the FileUtil to use
     */
    private TempFileUtil getFileUtil() {
        if (this.fileUtil == null) {
            LOGGER.warn("fileUtil not set. Maybe config error.");
        }
        return fileUtil;
    }
}
