/*
 * $Id: RangeListener.java,v 1.1 2009-01-15 11:46:26 kfuchsbe Exp $
 * 
 * $Date: 2009-01-15 11:46:26 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.machine;

import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;

/**
 * this interface defines methods, which are fired, when something in a range changes.
 * 
 * @author kfuchsbe
 */
public interface RangeListener {

    /**
     * fired, when a misalignment is added or removed.
     * 
     * @param misalignmentConfiguration the misalignement configuration which was added.
     */
    public void addedMisalignments(MisalignmentConfiguration misalignmentConfiguration);

}
