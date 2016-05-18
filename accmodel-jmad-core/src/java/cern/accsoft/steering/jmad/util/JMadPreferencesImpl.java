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
 * $Id: PreferencesImpl.java,v 1.2 2008-09-19 16:49:41 kfuchsbe Exp $
 * 
 * $Date: 2008-09-19 16:49:41 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util;

/**
 * The basic implementation to store preferences.
 * <p>
 * The basic principle is the following: For each option there exists a system
 * property and it can be st individually. If it is set individually then the
 * value is returned. If not set, then the value of the system property is
 * returned, if this is not set then a hardcoded default value is returned.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadPreferencesImpl implements JMadPreferences {

	/** The name of the system-property for the output path. */
	private static final String PROP_OUTPUT_PATH = "cern.jmad.output.path";
	/** the default value for the output path when property is not set. */
	private static final String DEFAULT_OUTPUT_PATH = ".";
	/** The output path, if set. Default to null so the system value is returned */
	private String outputPath = null;

	/** The name of the system-property for the model repository base path */
	private static final String PROP_REPOSITORY_BASE_PATH = "cern.jmad.repository.base.path";

	/**
	 * the repository base path if set. Defaults to null so the system property
	 * value is returned.
	 */
	private String repositoryBasePath = null;

	/** The name of the property defining the cleanup of the kernel files */
	private static final String PROP_CLEANUP_KERNEL_FILES = "cern.jmad.cleanup.kernel.files";
	/** Per default the kernel files shall be cleaned up */
	private static final boolean DEFAULT_CLEANUP_KERNEL_FILES = true;
	/**
	 * The value of cleaning up kernel files. Defaults to null so the property
	 * value is returned.
	 */
	private Boolean cleanupKernelFiles = null;

	//
	// methods of interface Preferences
	//

	
	@Override
	public String getOutputPath() {
		/* If the outputpath is set, then the answer is clear: we use it */
		if (this.outputPath != null) {
			return this.outputPath;
		}

		/* else use the property */
		String outputPath = System.getProperty(PROP_OUTPUT_PATH);
		if (outputPath != null) {
			return outputPath;
		}

		/* if even that is not set, then we use the system temp-dir: */
		outputPath = SystemUtil.getSystemTempDirectoryPath();
		if (outputPath != null) {
			return outputPath;
		}

		/*
		 * If even the system-temp dir is not available, then we use the default
		 * value
		 */
		return DEFAULT_OUTPUT_PATH;
	}

	@Override
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	@Override
	public String getModelRepositoryBasePath() {
		if (this.repositoryBasePath != null) {
			return this.repositoryBasePath;
		}
		return System.getProperty(PROP_REPOSITORY_BASE_PATH);
	}

	@Override
	public void setModelRepositoryBasePath(String basePath) {
		this.repositoryBasePath = basePath;
	}

	@Override
	public boolean isCleanupKernelFiles() {
		if (this.cleanupKernelFiles != null) {
			return this.cleanupKernelFiles;
		}
		String propertyValue = System.getProperty(PROP_CLEANUP_KERNEL_FILES);
		if (propertyValue != null) {
			return Boolean.parseBoolean(propertyValue);
		}
		return DEFAULT_CLEANUP_KERNEL_FILES;
	}

	@Override
	public void setCleanupKernelFiles(boolean cleanup) {
		this.cleanupKernelFiles = cleanup;
	}

}
