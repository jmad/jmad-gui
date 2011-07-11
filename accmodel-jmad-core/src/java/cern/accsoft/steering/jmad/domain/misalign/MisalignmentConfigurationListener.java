/*
 * $Id: MisalignmentConfigurationListener.java,v 1.1 2009-01-15 11:46:26 kfuchsbe Exp $
 * 
 * $Date: 2009-01-15 11:46:26 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.misalign;

/**
 * the interface for a listener to MisalignmentConfigurations
 * 
 * @author kfuchsbe
 */
public interface MisalignmentConfigurationListener {

    /**
     * fired when values of the misalignment in misalignmentConfiguration changed
     * 
     * @param misalignmentConfiguration the configuration, whose values were changed.
     */
    public void changedMisalignmentValues(MisalignmentConfiguration misalignmentConfiguration);

}
