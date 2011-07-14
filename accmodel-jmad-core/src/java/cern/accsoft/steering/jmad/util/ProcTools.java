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

public final class ProcTools {

    private ProcTools() {
        /* only static methods */
    }

    /**
     * @param process the process to test.
     * @return true, if the thread was started before, false otherwise
     */
    public static boolean isRunning(Process process) {
        if (process == null) {
            return false;
        }
        /*
         * XXX some dirty trick: Tries to get the exit value. If this is possible, then the thread already terminated.
         * If its not possible, then the process is still running. TODO better solution.
         */
        try {
            process.exitValue();
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }
}
