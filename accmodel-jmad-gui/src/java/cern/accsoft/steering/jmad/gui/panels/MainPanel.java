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
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import cern.accsoft.gui.beans.MultiSplitLayout;
import cern.accsoft.gui.beans.MultiSplitPane;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = -8292677890152652172L;

	/* the three panels */
	private JPanel modelOperationPanel = null;
	private JPanel dataViewerPanel = null;
	private JPanel outputPanel = null;
	private JPanel modelManagerPanel = null;

	/**
	 * init-method may be used by spring
	 */
	public void init() {
		initComponents();
	}

	/**
	 * initializes the components.
	 */
	private void initComponents() {
		setLayout(new BorderLayout());

		/*
		 * The multisplit layout and the multisplit-pane, which contains all
		 * others.
		 */
		String layoutDef = "(COLUMN (ROW weight=0.5 (LEAF name=left weight=0.2) (LEAF name=middle weight=0.5) (LEAF name=right weight=0.3)) (LEAF name=bottom weight=0.5))";
		MultiSplitLayout.Node layoutModel = MultiSplitLayout
				.parseModel(layoutDef);
		MultiSplitPane multiSplitPane = new MultiSplitPane();
		multiSplitPane.getMultiSplitLayout().setModel(layoutModel);
		multiSplitPane.getMultiSplitLayout().setDividerSize(5);
		add(multiSplitPane, BorderLayout.CENTER);

		/*
		 * left panel: all the available models
		 */
		if (getModelManagerPanel() != null) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			panel.add(new JLabel("Models"), BorderLayout.NORTH);
			panel.add(getModelManagerPanel(), BorderLayout.CENTER);
			multiSplitPane.add(panel, "left");
		}

		/*
		 * the middle panel: TABBED-panes
		 */
		if (modelOperationPanel != null) {
			modelOperationPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			multiSplitPane.add(modelOperationPanel, "middle");
		}

		/*
		 * the panel for the dataviewer views
		 */
		if (dataViewerPanel != null) {
			dataViewerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			multiSplitPane.add(dataViewerPanel, "bottom");
		}

		if (outputPanel != null) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			panel.add(new JLabel("Output"), BorderLayout.NORTH);
			panel.add(outputPanel, BorderLayout.CENTER);
			multiSplitPane.add(panel, "right");
		}

		multiSplitPane.validate();
		validate();
	}

	/**
	 * @param modelOperationPanel
	 *            the modelOperationPanel to set
	 */
	public final void setModelOperationPanel(JPanel modelOperationPanel) {
		this.modelOperationPanel = modelOperationPanel;
	}

	/**
	 * @param dataViewerPanel
	 *            the dataViewerPanel to set
	 */
	public final void setDataViewerPanel(JPanel dataViewerPanel) {
		this.dataViewerPanel = dataViewerPanel;
	}

	public void setOutputPanel(JPanel outputPanel) {
		this.outputPanel = outputPanel;
	}

	public void setModelManagerPanel(JPanel modelManagerPanel) {
		this.modelManagerPanel = modelManagerPanel;
	}

	private JPanel getModelManagerPanel() {
		return modelManagerPanel;
	}

}
