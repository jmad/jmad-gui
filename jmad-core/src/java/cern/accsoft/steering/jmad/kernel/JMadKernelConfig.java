package cern.accsoft.steering.jmad.kernel;

/**
 * Interface to configure JMadKernel behavior
 * 
 * @author muellerg
 */
public interface JMadKernelConfig {
    /**
     * @return the actual set timeout
     */
    public abstract Long getTimeout();

    /**
     * sets the actual timeout in ms. If null then the kernel waits forever for Madx.
     * 
     * @param timeout the timeout to set
     */
    public abstract void setTimeout(Long timeout);

    /**
     * sets the flag, if the output-file shall be kept or deleted after task/command execution.
     * 
     * @param keepOutputFile true if the output-file shall be kept, false otherwise
     */
    public abstract void setKeepOutputFile(boolean keepOutputFile);

    /**
     * @return true, if the outputfile shall be kept or false if it shall be deleted after command/task execution.
     */
    public abstract boolean isKeepOutputFile();

    /**
     * enable automatic deletion of JMadKernel tmpDir during shutdown
     * 
     * @param cleanupDirs true if tempDirectory should be deleted
     */
    public abstract void setCleanupDirs(boolean cleanupDirs);

    /**
     * @return true if the JMadKernel tempDirectory is going to be deleted on Shutdown
     */
    public abstract boolean isCleanupDirs();
}
