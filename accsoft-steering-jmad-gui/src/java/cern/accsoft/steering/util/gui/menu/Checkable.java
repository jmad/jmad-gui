/*
 * $Id: Checkable.java,v 1.1 2008-08-16 21:37:58 kfuchsbe Exp $
 * 
 * $Date: 2008-08-16 21:37:58 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.menu;

/**
 * this interface can be implemented by a TableModel in order to receive
 * commands from the popu menu.
 * 
 * @author kfuchsbe
 * 
 */
public interface Checkable {

	/**
	 * queried to determine, if to enable/disable menu-items
	 * 
	 * @return if check/uncheck items should be enabled
	 */
	public boolean isCheckUncheckEnabled();
	
	/**
	 * tells the TableModel to check all selected rows
	 */
	public void checkAllSelected();
	
	
	/**
	 * tells the TableModel to uncheck all selected rows
	 */
	public void uncheckAllSelected();
}
