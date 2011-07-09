/*
 * $Id: DataSetManager.java,v 1.1 2008-08-15 18:05:25 kfuchsbe Exp $
 * 
 * $Date: 2008-08-15 18:05:25 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.manage;

/**
 * this interface defines common methods for the DataSetManager
 * 
 * @author kfuchsbe
 *
 */
public interface DataSetManager {
	
	/**
	 * add a listener
	 * 
	 * @param listener the listener to add
	 */
	public void addListener(DataSetManagerListener listener);

	
	/**
	 * removes a listener
	 * 
	 * @param listener the listener to remove
	 */
	public void removeListener(DataSetManagerListener listener);
}
