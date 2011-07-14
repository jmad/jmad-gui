// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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

import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cern.accsoft.steering.util.gui.dialog.PanelDialog;
import cern.accsoft.steering.util.gui.panels.PanelProvider;

/**
 * This class provides methods for user-interaction through swing-dialogs.
 * 
 * @author kfuchsbe
 * 
 */
public class SwingUserInteractor implements UserInteractor {

	/** the mainPanel as parent for all dialogs */
	private Frame mainFrame;

	@Override
	public String askForString(String question, String defaultInput) {
		return JOptionPane.showInputDialog(getMainFrame(), question,
				defaultInput);
	}

	@Override
	public String askForString(String question) {
		return JOptionPane.showInputDialog(getMainFrame(), question);
	}

	@Override
	public boolean showPanelDialog(JPanel panel) {
		return PanelDialog.show(panel, getMainFrame());
	}

	@Override
	public boolean showPanelDialog(PanelProvider panelProvider) {
		return PanelDialog.show(panelProvider, getMainFrame());
	}

	/**
	 * @param mainFrame
	 *            the mainFrame to set
	 */
	public void setMainFrame(Frame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * @return the mainFrame
	 */
	public Frame getMainFrame() {
		return mainFrame;
	}

}
