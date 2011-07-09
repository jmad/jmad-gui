/*
 * $Id: TwissListener.java,v 1.1 2009-01-20 19:43:11 kfuchsbe Exp $
 * 
 * $Date: 2009-01-20 19:43:11 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.twiss;

/**
 * this interface contains methods, which are fired e.g. when values of the twiss change.
 * 
 * @author kfuchsbe
 */
public interface TwissListener {

    /**
     * fired, when values of the twiss changed.
     * 
     * @param twiss the twiss-object whose values changed.
     */
    public void changedTwiss(TwissInitialConditions twiss);
}
