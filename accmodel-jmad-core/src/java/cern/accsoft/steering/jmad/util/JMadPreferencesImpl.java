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
 * The basic principle is the following: For each option there exists a system property and it can be st individually.
 * If it is set individually then the value is returned. If not set, then the value of the system property is returned,
 * if this is not set then a hardcoded default value is returned.
 * 
 * @author kfuchsbe
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
    /** the base path to model repositories */
    private static final String DEFAULT_REPOSITORY_BASE_PATH = ".";
    /**
     * the repository base path if set. Defaults to null so the system property value is returned.
     */
    private String repositoryBasePath = null;

    /** The name of the property defining the cleanup of the kernel files */
    private static final String PROP_CLEANUP_KERNEL_FILES = "cern.jmad.cleanup.kernel.files";
    /** Per default the kernel files shall be cleaned up */
    private static final boolean DEFAULT_CLEANUP_KERNEL_FILES = true;
    /**
     * The value of cleaning up kernel files. Defaults to null so the property value is returned.
     */
    private Boolean cleanupKernelFiles = null;

    //
    // methods of interface Preferences
    //

    @Override
    public String getOutputPath() {
        if (this.outputPath != null) {
            return this.outputPath;
        }

        String outputPath = System.getProperty(PROP_OUTPUT_PATH, DEFAULT_OUTPUT_PATH);
        if (DEFAULT_OUTPUT_PATH.equals(outputPath)) {
            /* user has not specified any specific output path */
            /* check if we are in the ccc and avoid trouble */
            outputPath = this.ensureNonViolatingPath(outputPath);
        }

        return outputPath;
    }

    /**
     * If JMad is started in the CCC without any preferences given, the home directory is /user/OPERATOR_USER. There it
     * is not allowed to have read/write access for all users, but it is restricted to OPERATOR_USER. This method
     * checks, if the pc jmad is running on is a ccc console and set the output path to the systems temp folder. There
     * it is allowed to create the temp folder tree with the restrictions regarding read/write access.
     * 
     * @param outputPath the initial output path
     * @return the adapted output path
     */
    private String ensureNonViolatingPath(String outputPath) {
        String hostName = SystemUtil.getHostName();
        /* for now check that the host name does not contain 'ccc' */
        if (hostName.toLowerCase().contains("ccc")) {
            return SystemUtil.getSystemTempDirectoryPath();
        }

        return outputPath;
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
        return System.getProperty(PROP_REPOSITORY_BASE_PATH, DEFAULT_REPOSITORY_BASE_PATH);
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
