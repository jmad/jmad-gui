/*
 * $Id: MisalignmentListener.java,v 1.1 2009-01-15 11:46:26 kfuchsbe Exp $
 * 
 * $Date: 2009-01-15 11:46:26 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.misalign;

/**
 * this interface defines methods, which are fired when data in misalignment changes
 * 
 * @author kfuchsbe
 */
public interface MisalignmentListener {

    /**
     * fired, when some misalignment value changed
     * 
     * @param misalignment the misalignment, whose values changed
     */
    public void changedValues(Misalignment misalignment);
}
