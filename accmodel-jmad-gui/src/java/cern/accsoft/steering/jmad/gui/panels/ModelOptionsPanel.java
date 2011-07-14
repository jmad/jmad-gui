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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.ModelMode;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerListener;

/**
 * contains gui elements to configure the actual model
 * 
 * @author kaifox
 */
public class ModelOptionsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JMadModelManager modelManager;

    private JComboBox cboMode;

    private ActionListener cboListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMadModel model = getModelManager().getActiveModel();
            ModelMode mode = (ModelMode) cboMode.getSelectedItem();
            if ((model != null) && (mode != null)) {
                model.setMode(mode);
            }
        }
    };

    private JMadModelManagerListener modelManagerListener = new JMadModelManagerAdapter() {
        @Override
        public void changedActiveModel(JMadModel newActiveModel) {
            setModel(newActiveModel);
        }
    };

    public void init() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        this.cboMode = new JComboBox(ModelMode.values());
        setModel(getModelManager().getActiveModel());
        this.cboMode.addActionListener(this.cboListener);
        add(this.cboMode, constraints);
    }

    private void setModel(JMadModel model) {
        if (model != null) {
            cboMode.setSelectedItem(model.getMode());
        }
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
        this.modelManager.addListener(this.modelManagerListener);
    }

    private JMadModelManager getModelManager() {
        return modelManager;
    }

}
