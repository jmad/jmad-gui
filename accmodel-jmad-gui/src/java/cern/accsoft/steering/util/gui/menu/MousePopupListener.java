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
package cern.accsoft.steering.util.gui.menu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * extension for {@link MouseAdapter} to check if a popup menu shall show up.
 * 
 * @author kfuchsbe
 * 
 */
public class MousePopupListener extends MouseAdapter {

	private JPopupMenu popupMenu;
	private Component invoker;

	/**
	 * the constructor
	 * 
	 * @param invoker
	 *            the parent component
	 * @param popupMenu
	 *            the popupMenu that shall show up, when the mouse is clicked
	 */
	public MousePopupListener(Component invoker, JPopupMenu popupMenu) {
		this.invoker = invoker;
		this.invoker.addMouseListener(this);
		this.popupMenu = popupMenu;
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		super.mousePressed(evt);
		checkPopup(evt);
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		super.mouseReleased(evt);
		checkPopup(evt);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		super.mouseClicked(evt);
		checkPopup(evt);
	}

	private void checkPopup(MouseEvent evt) {
		if (evt.isPopupTrigger()) {
			popupMenu.show(invoker, evt.getX(), evt.getY());
		}
	}
}