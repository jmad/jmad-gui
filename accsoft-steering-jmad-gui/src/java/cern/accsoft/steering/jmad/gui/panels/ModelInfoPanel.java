/*
 * $Id: ModelInfoPanel.java,v 1.1 2008-08-11 11:58:45 kfuchsbe Exp $
 * 
 * $Date: 2008-08-11 11:58:45 $ 
 * $Revision: 1.1 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerListener;

/**
 * this panel just displays some information for the current selected model.
 * 
 * @author kfuchsbe
 * 
 */
public class ModelInfoPanel extends JPanel {
	private static final long serialVersionUID = 7716343012633544812L;

	/* the modelmanager which determines which is the actual model. */
	private JMadModelManager modelManager;

	private JMadModelManagerListener modelManagerListener = new JMadModelManagerAdapter() {
		@Override
		public void changedActiveModel(JMadModel newModel) {
			initComponents();
		}
	};

	/**
	 * the constructor
	 */
	public ModelInfoPanel() {
		super(new GridBagLayout());
	}

	/**
	 * the public initialization method
	 */
	public void init() {
		initComponents();
	}

	/**
	 * set the modelManager.
	 * 
	 * @param modelManager
	 */
	public void setModelManager(JMadModelManager modelManager) {
		this.modelManager = modelManager;
		modelManager.addListener(this.modelManagerListener);
	}

	/**
	 * creates all the components and displays the data.
	 */
	private void initComponents() {
		removeAll();

		if (modelManager == null) {
			return;
		}

		JMadModel model = modelManager.getActiveModel();

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;

		JLabel typeLabel = new JLabel();
		if (model != null) {
			typeLabel.setText("Type: " + model.getClass().getCanonicalName());
		} else {
			typeLabel.setText("Type: null");
		}
		constraints.gridy = 0;
		add(typeLabel, constraints);

		JLabel nameLabel = new JLabel();
		if (model != null) {
			nameLabel.setText("Name: " + model.getName());
		} else {
			nameLabel.setText("Name: null");
		}
		constraints.gridy = 1;
		add(nameLabel, constraints);
		
		JLabel descriptionLabel = new JLabel();
        if (model != null) {
            descriptionLabel.setText("Description: " + model.getDescription());
        } else {
            descriptionLabel.setText("Description: null");
        }
        constraints.gridy++;
        add(descriptionLabel, constraints);

		validate();
	}

}
