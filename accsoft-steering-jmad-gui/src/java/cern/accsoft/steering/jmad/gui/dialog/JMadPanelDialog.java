/*
 * $Id: JMadPanelDialog.java,v 1.1 2008-08-18 19:22:07 kfuchsbe Exp $
 * 
 * $Date: 2008-08-18 19:22:07 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.dialog;

import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.util.gui.dialog.SimplePanelDialog;

/**
 * The same as a panel - dialog, but has the MADX icon on top.
 * 
 * @author kfuchsbe
 *
 */
public class JMadPanelDialog extends SimplePanelDialog {
	private static final long serialVersionUID = -4191349467425767648L;

	public JMadPanelDialog() {
		super();
		setIconImage(Icon.MADX.getImageIcon().getImage());
	}
}
