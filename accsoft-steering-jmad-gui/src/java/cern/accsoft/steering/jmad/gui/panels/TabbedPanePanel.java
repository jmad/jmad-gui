/*
 * $Id: ModelOperationPanel.java,v 1.4 2009-01-15 11:46:28 kfuchsbe Exp $
 * 
 * $Date: 2009-01-15 11:46:28 $ $Revision: 1.4 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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
