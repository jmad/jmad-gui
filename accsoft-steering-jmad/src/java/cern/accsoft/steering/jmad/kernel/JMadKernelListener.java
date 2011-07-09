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
