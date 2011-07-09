/*
 * $Id: UserInteractor.java,v 1.2 2008-10-28 17:58:40 kfuchsbe Exp $
 * 
 * $Date: 2008-10-28 17:58:40 $ 
 * $Revision: 1.2 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui;

import javax.swing.JPanel;

import cern.accsoft.steering.util.gui.panels.PanelProvider;

/**
 * this interface provides some simple methods for user-interaction.
 * 
 * @author kfuchsbe
 * 
 */
public interface UserInteractor {

	/**
	 * @param question
	 *            the the String, which describes to the user, what to enter.
	 * @return the answer. Null, if aborted
	 */
	public String askForString(String question);

	/**
	 * @param question
	 *            the the String, which describes to the user, what to enter.
	 * @param defaultInput
	 * 			  the string to display as default for the input
	 * @return the answer. Null, if aborted
	 */
	public String askForString(String question, String defaultInput);
	
	/**
	 * opens a dialog with the given panel
	 * 
	 * @param panel
	 *            the panel to show in the dialog
	 * @return true, if the ok-button was pressed, false if not
	 */
	public boolean showPanelDialog(JPanel panel);

	/**
	 * opens a dialog with the panel of the given panelProvider
	 * 
	 * @param panelProvider
	 *            the panelProvider which provides the panel to show
	 * @return true, if the ok button was pressed, false otherwise
	 */
	public boolean showPanelDialog(PanelProvider panelProvider);
}
