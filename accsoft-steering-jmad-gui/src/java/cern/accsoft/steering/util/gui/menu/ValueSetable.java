/*
 * $Id: ValueSetable.java,v 1.1 2008-08-16 21:37:58 kfuchsbe Exp $
 * 
 * $Date: 2008-08-16 21:37:58 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.menu;

/**
 * this interface can be implemented by a tableModel to enable the setting of
 * double-values via popup menu.
 * 
 * @author kfuchsbe
 * 
 */
public interface ValueSetable {

	/**
	 * is queried by the popup menu to determine, if it shall enable the
	 * setValue-functionality or not.
	 * 
	 * @return true if enabled, false if not
	 */
	public boolean isValueSetEnabled();
	
	/**
	 * sets the Value to all selected rows.
	 * 
	 * @param value the value to set.
	 */
	public void setValueAllSelected(Double value);
	
	
	/**
	 * has to return the name of the value in order to display it for the user.
	 * 
	 * @return the name of the value
	 */
	public String getValueName();
}
