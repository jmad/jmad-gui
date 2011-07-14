// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
