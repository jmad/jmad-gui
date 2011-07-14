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
package cern.accsoft.steering.util.gui.dialog;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class SimplePanelDialog extends JDialog {
	private static final long serialVersionUID = 1695446208261983899L;

	/**
	 * intit method for spring
	 */
	public void init() {
		pack();
	}
	
	/**
	 * sets the panel as content
	 * 
	 * @param panel
	 */
	public void setPanel(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
	}
}
