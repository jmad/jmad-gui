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

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinition;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.util.gui.panels.Applyable;
import cern.accsoft.steering.util.gui.panels.Titleable;

public class RangeSelectionPanel extends JPanel implements Titleable, Applyable {
    private final static Logger logger = Logger.getLogger(RangeSelectionPanel.class);

    private static final long serialVersionUID = -3095464304653777882L;

    /** the model-manager, which keeps track of the selected model. */
    private JMadModelManager modelManager;

    private JComboBox cboSequence;
    private JComboBox cboRange;

    private ActionListener rangeComboListener;
    private ActionListener sequenceComboListener;

    /** the active model definition */
    private JMadModelDefinition activeModelDefinition = null;

    public void init() {
        initComponenets();
    }

    private void initComponenets() {
        setLayout(new BorderLayout());

        /*
         * Combo-Boxes
         */
        JPanel comboPanel = new JPanel(new GridBagLayout());
        add(comboPanel, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;

        comboPanel.add(new JLabel("Sequence: "), constraints);

        constraints.gridx++;
        cboSequence = new JComboBox();
        comboPanel.add(cboSequence, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        comboPanel.add(new JLabel("Range: "), constraints);

        constraints.gridx++;
        cboRange = new JComboBox();
        comboPanel.add(cboRange, constraints);

        /*
         * if a model manager is already available then we try to set the model.
         */
        if (this.modelManager != null) {
            JMadModel model = modelManager.getActiveModel();
            if (model != null) {
                setActiveModel(model);
            }
        }
    }

    /**
     * setter used for dependency injection. Sets the actual model-manager and adds a listener in order to get informed,
     * if something happens according to model-changes.
     * 
     * @param modelManager
     */
    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
        modelManager.addListener(new JMadModelManagerAdapter() {
            @Override
            public void changedActiveModel(JMadModel model) {
                setActiveModel(model);
            }
        });
    }

    void setActiveModelDefinition(JMadModelDefinition modelDefinition, RangeDefinition rangeDefinition) {
        this.activeModelDefinition = modelDefinition;
        fillSequenceSelectionComboBox();
        if (rangeDefinition != null) {
            setActiveSequenceDefinition(rangeDefinition.getSequenceDefinition());
            setActiveRangeDefinition(rangeDefinition);
        }
    }

    /**
     * sets the values of all comboboxes to the values given by the model.
     * 
     * @param model
     */
    void setActiveModel(JMadModel model) {
        if (model == null) {
            this.setActiveModelDefinition(null, null);
        } else {
            this.setActiveModelDefinition(model.getModelDefinition(), model.getActiveRangeDefinition());
        }
    }

    /**
     * sets the active Sequence and fills the Range-Combo-Box accordingly
     * 
     * @param sequence
     */
    private void setActiveSequenceDefinition(SequenceDefinition sequenceDefinition) {
        this.cboSequence.setSelectedItem(sequenceDefinition);
        fillRangeSelectionComboBox();
    }

    /**
     * @return the actual selected {@link SequenceDefinition}
     */
    private SequenceDefinition getActiveSequenceDefinition() {
        return (SequenceDefinition) this.cboSequence.getSelectedItem();
    }

    /**
     * sets the actice range to the combobox.
     * 
     * @param rangeDefinition
     */
    private void setActiveRangeDefinition(RangeDefinition rangeDefinition) {
        this.cboRange.setSelectedItem(rangeDefinition);
    }

    /**
     * @return the currently selected range definition
     */
    RangeDefinition getActiveRangeDefinition() {
        return (RangeDefinition) this.cboRange.getSelectedItem();
    }

    private void fillSequenceSelectionComboBox() {
        cboSequence.removeActionListener(getSequenceComboListener());
        cboSequence.removeAllItems();
        if (getActiveModelDefinition() == null) {
            return;
        }

        for (SequenceDefinition sequenceDefinition : getActiveModelDefinition().getSequenceDefinitions()) {
            cboSequence.addItem(sequenceDefinition);
        }
        cboSequence.setSelectedItem(getActiveModelDefinition().getDefaultSequenceDefinition());
        cboSequence.addActionListener(getSequenceComboListener());
    }

    private JMadModelDefinition getActiveModelDefinition() {
        return this.activeModelDefinition;
    }

    private void fillRangeSelectionComboBox() {
        cboRange.removeActionListener(getRangeComboListener());
        cboRange.removeAllItems();
        for (RangeDefinition rangeDefinition : getActiveSequenceDefinition().getRangeDefinitions()) {
            cboRange.addItem(rangeDefinition);
        }
        cboRange.setSelectedItem(getActiveSequenceDefinition().getDefaultRangeDefinition());
        cboRange.addActionListener(getRangeComboListener());
    }

    private ActionListener getSequenceComboListener() {
        if (sequenceComboListener == null) {
            sequenceComboListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fillRangeSelectionComboBox();
                }
            };
        }
        return sequenceComboListener;
    }

    private ActionListener getRangeComboListener() {
        if (rangeComboListener == null) {
            rangeComboListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /*
                     * nothing to do, work is performed, when apply is pressed
                     */
                }
            };
        }
        return rangeComboListener;
    }

    @Override
    public String getTitle() {
        return "Range selection";
    }

    @Override
    public boolean apply() {
        JMadModel model = this.modelManager.getActiveModel();
        if (model == null) {
            return false;
        }

        RangeDefinition rangeDefinition = getActiveRangeDefinition();
        if (rangeDefinition == null) {
            return false;
        }
        try {
            model.setActiveRangeDefinition(rangeDefinition);
            return true;
        } catch (JMadModelException e) {
            logger.error("Error while setting range '" + rangeDefinition.getName() + "' Model.", e);
            return false;
        }
    }

    @Override
    public void cancel() {
        /* do nothing */
    }
}
