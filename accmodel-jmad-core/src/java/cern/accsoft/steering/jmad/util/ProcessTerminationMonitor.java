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
package cern.accsoft.steering.jmad.util;

import org.apache.log4j.Logger;

public class ProcessTerminationMonitor extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ProcessTerminationMonitor.class);
    private static final long POLL_INTERVALL = 10;

    private final Process process;

    public ProcessTerminationMonitor(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        while (ProcTools.isRunning(process)) {
            try {
                sleep(POLL_INTERVALL);
            } catch (InterruptedException e) {
                LOGGER.warn("Waiting for terminating process '" + process.toString() + "' was interrupted.", e);
            }
        }
    }

}
