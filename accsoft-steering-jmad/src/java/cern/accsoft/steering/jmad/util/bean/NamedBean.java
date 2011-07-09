/*
 * $Id: NamedBean.java,v 1.1 2009-01-20 19:43:10 kfuchsbe Exp $
 * 
 * $Date: 2009-01-20 19:43:10 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.util.bean;

/**
 * this interface defines just one method: the one to return the name.
 * 
 * @author kfuchsbe
 */
public interface NamedBean {

    /**
     * @return the name of the instance
     */
    public String getName();
}
