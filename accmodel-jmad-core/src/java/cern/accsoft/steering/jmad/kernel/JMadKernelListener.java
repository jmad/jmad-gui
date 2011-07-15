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
