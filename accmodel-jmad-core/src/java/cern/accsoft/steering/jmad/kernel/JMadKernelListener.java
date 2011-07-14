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
/*
 * $Id: MadXKernelListener.java,v 1.1 2008-12-19 13:55:31 kfuchsbe Exp $
 * 
 * $Date: 2008-12-19 13:55:31 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.kernel;

/**
 * this interface defines the methods which are fired, when the kernel-state changes.
 * 
 * @author kfuchsbe
 */
public interface JMadKernelListener {

    /**
     * fired, when the kernel is stared.
     * 
     * @param newProcess the process, which represents the running madx-binary.
     */
    public void startedKernel(Process newProcess);

    /**
     * fired, when the kernel stops.
     */
    public void stoppedKernel();
}
