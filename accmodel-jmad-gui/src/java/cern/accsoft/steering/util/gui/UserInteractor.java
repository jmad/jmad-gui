// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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
