/*
 * $Id: ActionProvider.java,v 1.1 2009-01-20 19:43:10 kfuchsbe Exp $
 * 
 * $Date: 2009-01-20 19:43:10 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.menu;

import java.util.List;

import javax.swing.Action;

/**
 * simple interface, that provides actions
 * 
 * @author kfuchsbe
 * 
 */
public interface ActionProvider {

	/**
	 * @return a list of actions
	 */
	public List<Action> getActions();
}
