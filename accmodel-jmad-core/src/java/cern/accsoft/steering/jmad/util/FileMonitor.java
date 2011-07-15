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

package cern.accsoft.steering.jmad.util;

import java.io.File;

import cern.accsoft.steering.jmad.JMadException;

/**
 * polls a file and terminates as soon as it exists.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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
