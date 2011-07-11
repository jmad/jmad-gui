/*
 * $Id: PanelProvider.java,v 1.1 2008-10-28 17:58:40 kfuchsbe Exp $
 * 
 * $Date: 2008-10-28 17:58:40 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui.panels;

import javax.swing.JPanel;

/**
 * this interface just provides an panel
 * 
 * @author kfuchsbe
 * 
 */
public interface PanelProvider {

	/**
	 * @return the panel, which can for example be integrated in another panel,
	 *         or shown in a dialog
	 */
	public JPanel getPanel();
}
