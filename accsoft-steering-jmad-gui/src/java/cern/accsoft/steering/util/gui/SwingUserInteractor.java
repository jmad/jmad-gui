/*
 * $Id: SwingUserInteractor.java,v 1.2 2008-10-28 17:58:41 kfuchsbe Exp $
 * 
 * $Date: 2008-10-28 17:58:41 $ 
 * $Revision: 1.2 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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
