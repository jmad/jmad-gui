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

import java.io.File;

import cern.accsoft.steering.jmad.JMadException;
import cern.accsoft.steering.jmad.domain.result.Result;

/**
 * this is the interface for central class which controls an instance of MadX. The kernel only is able to do the most
 * primitives tasks: start the MadX-process, stop it and send commands to it. Furthermore it takes care of the logging
 * of input and output of the MadX process. Listeners can also be attached, which then get notified, when the status of
 * the kernel changes.
 * 
 * @author kfuchsbe
 */
public interface JMadKernel {

    /**
     * starts madx as a separate thread and configures the needed streams.
     * 
     * @throws JMadException if the starting of the MadX process fails
     */
    public abstract void start() throws JMadException;

    /**
     * stops the madx-thread
     * 
     * @return the exit-value of madx
     * @throws JMadException if the stopping of the MadX process fails
     */
    public abstract int stop() throws JMadException;

    /**
     * executes a Command or Task, waits for completion (our timeout if set), and returns result, if command/task
     * provides one.
     * 
     * @param executable the command or task to execute
     * @return the result, if available, otherwise null
     * @throws JMadException if the execution fails
     */
    public abstract Result execute(JMadExecutable executable) throws JMadException;

    /**
     * @return true, if madx was started before, false otherwise
     */
    public abstract boolean isMadxRunning();

    /**
     * @param listener the listener to add
     */
    public abstract void addListener(JMadKernelListener listener);

    /**
     * @param listener the listener to remove
     */
    public abstract void removeListener(JMadKernelListener listener);

    /**
     * @return the JMadKernel OutputFile
     */
    public abstract File getOutputFile();
}
