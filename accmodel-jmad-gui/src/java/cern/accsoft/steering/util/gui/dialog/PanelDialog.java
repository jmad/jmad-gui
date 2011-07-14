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
package cern.accsoft.steering.util.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cern.accsoft.steering.util.gui.panels.Applyable;
import cern.accsoft.steering.util.gui.panels.PanelProvider;
import cern.accsoft.steering.util.gui.panels.Titleable;
import cern.accsoft.steering.util.gui.panels.ValidateApplyable;

/**
 * A dialog, which contains a panel.
 * 
 * @author kfuchsbe
 * 
 */
public class PanelDialog extends JDialog {
	private static final long serialVersionUID = 67399462987558666L;

	/** the logger for the class */
	private final static Logger logger = Logger.getLogger(PanelDialog.class);

	/** the content, which we display */
	private Object content;

	/** the displayed content-panel */
	private JPanel panel;

	/** the value, which we will return */
	private boolean ok = false;

	/**
	 * the constructor
	 * 
	 * @param panel
	 *            the containing panel
	 * @param parentFrame
	 *            the parent frame
	 */
	private PanelDialog(Object content, Frame parentFrame, boolean modal) {
		super(parentFrame, modal);
		this.content = content;
		if (getContent() instanceof JPanel) {
			this.setPanel((JPanel) getContent());
		} else if (getContent() instanceof PanelProvider) {
			this.setPanel(((PanelProvider) getContent()).getPanel());
		} else {
			logger.error("can not add content of type '"
					+ getContent().getClass().getName() + "' to contentPane!");
		}
		initComponents();
	}

	/**
	 * private static method to splash up the dialog
	 * 
	 * @param content
	 *            the content, which to display in the dialog
	 * @param parentFrame
	 *            the parent frame
	 * @return true, if ok was pressed. false otherwise
	 */
	private final static boolean showContent(Object content, Frame parentFrame,
			boolean modal) {
		PanelDialog dialog = new PanelDialog(content, parentFrame, modal);
		dialog.setVisible(true);
		return dialog.isOk();
	}

	/**
	 * public static method to directly display a panel
	 * 
	 * @param panel
	 *            the panel
	 * @param parentFrame
	 *            the parent frame
	 * @return true, if ok pressed, false otherwise
	 */
	public final static boolean show(JPanel panel, Frame parentFrame) {
		return showContent(panel, parentFrame, true);
	}

	public final static boolean show(JPanel panel, Frame parentFrame,
			boolean modal) {
		return showContent(panel, parentFrame, modal);
	}

	/**
	 * public static method to display the panel of a {@link PanelProvider}
	 * 
	 * @param panelProvider
	 *            the {@link PanelProvider}
	 * @param parentFrame
	 *            the parent frame
	 * @return true, if ok pressed, false otherwise
	 */
	public final static boolean show(PanelProvider panelProvider,
			Frame parentFrame) {
		return showContent(panelProvider, parentFrame, true);
	}

	public final static boolean show(PanelProvider panelProvider,
			Frame parentFrame, boolean modal) {
		return showContent(panelProvider, parentFrame, modal);
	}

	/**
	 * create all the containing components
	 */
	private void initComponents() {
		String title = "User input";
		if (getPanel() instanceof Titleable) {
			title = ((Titleable) getPanel()).getTitle();
		}
		setTitle(title);

		if (isModal()) {
			/*
			 * If we have a modal dialog, it shall only be closed by a click on
			 * ok or cancel
			 */
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}

		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(this.getPanel(), BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		if (isModal()) {
			/* If we hava modal dialog, then we us ok-cancel. */
			buttonPanel.add(new JButton(createOkAction()));
			buttonPanel.add(new JButton(createCancelAction()));
		} else {
			/* If not modal, then we have just one close button. */
			buttonPanel.add(new JButton(createCloseAction()));
		}
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(getParent());
		pack();
	}

	/**
	 * @return the action for just closing the dialog
	 */
	private Action createCancelAction() {
		Action action = new AbstractAction() {
			private static final long serialVersionUID = -8624416405029781753L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				cancel();
				setVisible(false);
			}
		};
		action.putValue(AbstractAction.NAME, "Cancel");
		action.putValue(AbstractAction.SHORT_DESCRIPTION,
				"Closes dialog without applying changes.");

		return action;
	}

	/**
	 * @return the action for applying changes and closing the dialog
	 */
	private Action createOkAction() {
		Action action = new AbstractAction() {
			private static final long serialVersionUID = -8624416405029781753L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				boolean doIt = true;
				if (getContent() instanceof ValidateApplyable) {
					doIt = ((ValidateApplyable) getContent()).prepareApply();
				}
				if (doIt) {
					apply();
					setVisible(false);
				}
			}
		};
		action.putValue(AbstractAction.NAME, "Ok");
		action.putValue(AbstractAction.SHORT_DESCRIPTION,
				"Saves changes and closes dialog.");

		return action;
	}

	private Action createCloseAction() {
		Action action = new AbstractAction() {
			private static final long serialVersionUID = -8624416405029781753L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		};
		action.putValue(AbstractAction.NAME, "Close");
		action.putValue(AbstractAction.SHORT_DESCRIPTION, "Closes the dialog.");

		return action;
	}

	/**
	 * performs the apply in the panel, if it is {@link Applyable}
	 * 
	 * @return the return-value of the apply in the panel, or true.
	 */
	private void apply() {
		if (getContent() instanceof Applyable) {
			this.ok = ((Applyable) getContent()).apply();
		}
		this.ok = true;
	}

	private void cancel() {
		if (getContent() instanceof Applyable) {
			((Applyable) getContent()).cancel();
		}
		this.ok = false;
	}

	/**
	 * @return the containing panel
	 */
	private Object getContent() {
		return this.content;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param panel
	 *            the panel to set
	 */
	private void setPanel(JPanel panel) {
		this.panel = panel;
	}

	/**
	 * @return the panel
	 */
	private JPanel getPanel() {
		return panel;
	}

}
