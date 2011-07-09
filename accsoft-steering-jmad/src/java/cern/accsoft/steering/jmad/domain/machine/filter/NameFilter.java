/*
 * $Id: NameFilter.java,v 1.2 2008-08-25 13:52:12 kfuchsbe Exp $
 * 
 * $Date: 2008-08-25 13:52:12 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.machine.filter;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * simple interface to define e filter (e.g. for elements)
 * 
 * @author kfuchsbe
 */
public interface NameFilter {

    /**
     * @param name the name to check
     * @param plane the plane to check
     * @return <code>true</code> if the name is concerned by the filter in the given plane, <code>false</code>otherwise.
     */
    boolean isConcerned(String name, JMadPlane plane);
}
