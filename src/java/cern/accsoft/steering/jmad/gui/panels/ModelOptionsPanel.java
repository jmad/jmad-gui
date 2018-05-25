// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
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
