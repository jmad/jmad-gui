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
package cern.accsoft.steering.jmad.gui.menu;

import java.util.List;

import javax.swing.Action;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/**
 * The factory for toolbar and menu
 * 
 * @author kfuchsbe
 * 
 */
public interface JMadMenuFactory {

	/**
	 * @return a new toolbar for jmad.
	 */
	public JToolBar createToolBar();

	/**
	 * @return a new menubar for the JMad GUI
	 */
	public JMenuBar createMenuBar();

	/**
	 * @return the list of actions in the toolbar.
	 */
	public List<Action> getToolBarActions();

}
