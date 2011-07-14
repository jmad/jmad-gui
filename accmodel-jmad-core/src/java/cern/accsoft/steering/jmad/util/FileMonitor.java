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
package cern.accsoft.steering.jmad.util;

import java.io.File;

import cern.accsoft.steering.jmad.JMadException;

/**
 * polls a file and terminates as soon as it exists.
 * 
 * @author kfuchsbe
 */
public class FileMonitor {
    /** the sleeping interval between the polls. in milliseconds. */
    private static final int POLL_INTERVAL = 10;

    /** The file to wait for */
    private File file = null;

    /** The process to monitor. If this is interrupted, then the waiting throws an exception */
    private Process process = null;

    /**
     * waits forever, until the file exists.
     * 
     * @return true, if it exists finally, false, if the monitored process was interrupted
     * @throws WaitingFailedException if something goes wrong during waiting
     */
    public boolean waitForFile() throws WaitingFailedException {
        return waitForFile(null);
    }

    /**
     * waits until a file exists.
     * 
     * @param timeout the maximum millisecs to wait (if null, then we wait forever ...)
     * @return true, if the file finally exists, false, if the monitored process was interrupted or we timed out.
     * @throws WaitingFailedException if something goes wrong during the waiting
     */
    public synchronized boolean waitForFile(Long timeout) throws WaitingFailedException {
        long startTime = System.currentTimeMillis();
        while (!file.exists()) {
            /* interrupt if the process has stopped meanwhile. */
            if ((process != null) && (!ProcTools.isRunning(process))) {
                throw new WaitingFailedException("process '" + process.toString()
                        + "' terminated while waiting for file '" + file.getAbsolutePath()
                        + "' - maybe there was some error!");
            }

            /* interrupt if the timeout is reached */
            if (timeout != null) {
                long delay = timeout - (System.currentTimeMillis() - startTime);
                if (delay <= 0) {
                    return false;
                }
            }

            /*
             * If everything is ok so far, we wait a little bit before continuing.
             */
            try {
                this.wait(POLL_INTERVAL);
            } catch (InterruptedException e) {
                throw new WaitingFailedException("waiting for file '" + file.getAbsolutePath() + "' was interrupted", e);
            }
        }

        /* the file finally exists */
        return true;
    }

    public FileMonitor(File file) {
        this(file, null);
    }

    public FileMonitor(File file, Process process) {
        this.file = file;
        this.process = process;
    }

    /**
     * Will be thrown, if the waiting fails for some reason.
     * 
     * @author kaifox
     */
    public static class WaitingFailedException extends JMadException {
        private static final long serialVersionUID = 1L;

        public WaitingFailedException(String message) {
            super(message);
        }

        public WaitingFailedException(String message, Throwable cause) {
            super(message, cause);
        }

    }
}
