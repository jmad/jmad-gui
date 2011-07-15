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
 * $Id: Preferences.java,v 1.1 2008-09-09 21:09:34 kfuchsbe Exp $
 * 
 * $Date: 2008-09-09 21:09:34 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util;

/**
 * methods to handle settings that must be configurable by an user-application
 * 
 * @author kfuchsbe
 */
public interface JMadPreferences {

    /**
     * @param outputPath the path where jmad shall write its data
     */
    public void setOutputPath(String outputPath);

    /**
     * @return the path where jmad shall write its data
     */
    public String getOutputPath();

    /**
     * @return the base path to the model repository.
     */
    public String getModelRepositoryBasePath();

    /**
     * sets the base-path to the model repository.
     * 
     * @param basePath the new path
     */
    public void setModelRepositoryBasePath(String basePath);

    /**
     * @return <code>true</code> if the files created by the kernel should be cleaned up at the end, <code>false</code>
     *         if not.
     */
    public boolean isCleanupKernelFiles();

    /**
     * set to <code>true</code> if the files created by the kernel should be cleaned up at the end, <code>false</code>
     * if not.
     * 
     * @param cleanup the value to set
     */
    public void setCleanupKernelFiles(boolean cleanup);
}
