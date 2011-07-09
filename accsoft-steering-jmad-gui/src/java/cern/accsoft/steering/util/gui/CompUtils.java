/*
 * $Id: CompUtils.java,v 1.1 2009-01-15 11:46:27 kfuchsbe Exp $
 * 
 * $Date: 2009-01-15 11:46:27 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.util.gui;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * commun util-functions creating components etc.
 * 
 * @author kfuchsbe
 * 
 */
public class CompUtils {

	private CompUtils() {
		// only static methods
	}

	/**
	 * creates a scrollpane with the given component embedded.
	 * 
	 * @param content
	 *            the {@link JComponent} to embed
	 * @return the scrollpane with the embedded component
	 */
	public final static JScrollPane createScrollPane(JComponent content) {
		JScrollPane scrollPane = new JScrollPane(content,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		return scrollPane;
	}
}
