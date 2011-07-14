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
