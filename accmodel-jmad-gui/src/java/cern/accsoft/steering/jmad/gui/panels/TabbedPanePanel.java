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
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * A very simple panel, to which several sub-panels can be set via a map. When
 * the panel is initialized, then all the panels are shown as tabbed panes with
 * their keys as tab title.
 * 
 * @author kfuchsbe
 */
public class TabbedPanePanel extends JPanel {
	private static final long serialVersionUID = 7123876964118824309L;

	/** The default dimension of this panel */
	private final static Dimension PREFERRED_SIZE = new Dimension(400, 300);

	/** all the components that will be shown as tabbed pane afterwards */
	private Map<String, Component> tabbedPaneComponents = new HashMap<String, Component>();

	/**
	 * constructor
	 */
	public TabbedPanePanel() {
		super(new BorderLayout());
		setPreferredSize(PREFERRED_SIZE);
	}

	/**
	 * init function that can be used to initialize the panel, after all
	 * components are set.
	 */
	public void init() {
		initComponents();
	}

	/**
	 * adds all the components to the panel as tabbed panes with their keys as
	 * tabbed-pane title
	 */
	private void initComponents() {
		removeAll();

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		for (Entry<String, Component> entry : this.getTabbedPaneComponents()
				.entrySet()) {
			if (entry.getValue() != null) {
				tabbedPane.add(entry.getKey(), entry.getValue());
			}
		}

		validate();
	}

	public Map<String, Component> getTabbedPaneComponents() {
		return tabbedPaneComponents;
	}

	public void setTabbedPaneComponents(
			Map<String, Component> tabbedPaneComponents) {
		this.tabbedPaneComponents = tabbedPaneComponents;
	}
}
