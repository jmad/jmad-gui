/*
 * $Id: Applyable.java,v 1.1 2008-10-10 14:12:43 kfuchsbe Exp $
 * 
 * $Date: 2008-10-10 14:12:43 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.panels;

/**
 * This interface enforces an apply-method for a panel
 * 
 * @author kfuchsbe
 * 
 */
public interface Applyable {

	/**
	 * apply the changes
	 * 
	 * @return true, if everything is ok, false otherwise
	 */
	public boolean apply();

	/**
	 * undo the changes
	 */
	public void cancel();
}
