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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.plaf.TabbedPaneUI;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.gui.data.TfsResultDataSet.TfsResultDataSetType;
import cern.accsoft.steering.jmad.gui.manage.TfsDataSetManager;
import cern.accsoft.steering.jmad.gui.panels.var.AllVarSelectionPanel;
import cern.accsoft.steering.jmad.gui.panels.var.MadxVarSelector;
import cern.accsoft.steering.jmad.gui.panels.var.SelectedVarManager;
import cern.accsoft.steering.jmad.gui.panels.var.SelectedVarManager.VarSelectionMode;
import cern.accsoft.steering.jmad.gui.panels.var.SelectedVarManagerImpl;
import cern.accsoft.steering.util.gui.panels.Applyable;
import cern.accsoft.steering.util.gui.panels.Titleable;

/**
 * This panel allows the creation of different datasets.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public abstract class PlotCreationPanel extends JPanel implements Titleable, Applyable {
    private static final long serialVersionUID = -8324977554578482096L;

    /** The maximum amount of y-panels */
    private final static int MAX_Y_PANELS = 9;

    /** The dataset manager */
    private TfsDataSetManager tfsDataSetManager = null;

    /*
     * some gui-components we need
     */
    private JTextField txtName = null;
    private JComboBox cboTfsDataSetType = null;
    private JTabbedPane varSelTabPane = null;
    private AllVarSelectionPanel xPanel = null;
    private List<AllVarSelectionPanel> yPanels = new ArrayList<AllVarSelectionPanel>();

    /** just a panel to add, where a click creates a new panel */
    private JPanel newYPanel = new JPanel();

    /** just a panel to add, where a click deletes the last panel */
    private JPanel delLastYPanel = new JPanel();

    /**
     * simple constructors
     */
    public PlotCreationPanel() {
        initComponents();
    }

    /**
     * initializes the components.
     */
    private void initComponents() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 300));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;

        JLabel label;
        label = new JLabel("Name:");
        add(label, constraints);

        constraints.gridx++;
        constraints.weightx = 1;
        txtName = new JTextField();
        txtName.setToolTipText("The name of the new chart.");
        add(txtName, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy++;
        constraints.weightx = 1;
        constraints.weighty = 0;
        cboTfsDataSetType = new JComboBox(TfsResultDataSetType.values());
        cboTfsDataSetType.setSelectedItem(TfsResultDataSetType.ABSOLUTE);
        cboTfsDataSetType.setToolTipText("Selects the type of datasat (absolute, relative ...)");
        add(cboTfsDataSetType, constraints);

        constraints.gridy++;
        constraints.weighty = 1;
        /*
         * the panels to select the variables
         */
        this.varSelTabPane = new JTabbedPane();
        varSelTabPane.setTabPlacement(JTabbedPane.LEFT);
        add(varSelTabPane, constraints);

        /* for x-axis: only one var selectable */
        this.xPanel = createVariableSelectionPanel(VarSelectionMode.SINGLE, MadxTwissVariable.S);
        varSelTabPane.addTab("x", xPanel);

        /* per default: create one y-panel */
        AllVarSelectionPanel yPanel = createVariableSelectionPanel(VarSelectionMode.MULTIPLE, null);
        this.yPanels.add(yPanel);
        varSelTabPane.addTab("y", yPanel);

        varSelTabPane.addTab("+", this.newYPanel);

        varSelTabPane.setSelectedComponent(yPanel);

        varSelTabPane.addMouseListener(new MouseAdapter() {
            private Boolean editing = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                synchronized (editing) {
                    if (editing) {
                        return;
                    }
                }
                editing = true;
                TabbedPaneUI ui = varSelTabPane.getUI();
                int tab = ui.tabForCoordinate(varSelTabPane, e.getX(), e.getY());
                if (tab >= 0) {
                    JComponent selectedComponent = (JComponent) varSelTabPane.getComponentAt(tab);
                    if (selectedComponent == newYPanel) {
                        addRemoveYPanel(true);
                    } else if (selectedComponent == delLastYPanel) {
                        addRemoveYPanel(false);
                    }
                }
                synchronized (editing) {
                    editing = false;
                }
            }
        });
    }

    /**
     * Create a new y-panel and adds it to the tabs
     */
    private void addRemoveYPanel(boolean add) {
        /*
         * first of all we remove the 'plus tab'
         */
        varSelTabPane.remove(this.newYPanel);
        varSelTabPane.remove(this.delLastYPanel);

        AllVarSelectionPanel yPanel = null;

        if (add) {
            /*
             * if this is the first added, then we also have to remove the first one and re-add it with a different
             * name.
             */
            if (this.yPanels.size() == 1) {
                varSelTabPane.removeTabAt(varSelTabPane.getTabCount() - 1);
                varSelTabPane.addTab("y1", this.yPanels.get(0));
            }

            yPanel = createVariableSelectionPanel(VarSelectionMode.MULTIPLE, null);
            this.yPanels.add(yPanel);
            varSelTabPane.addTab("y" + this.yPanels.size(), yPanel);
        } else {
            /* remove the last */
            this.yPanels.remove(this.yPanels.size() - 1);
            varSelTabPane.removeTabAt(varSelTabPane.getTabCount() - 1);

            /*
             * if there is only one left, then we also have to remove the first one and re-add it with a different name.
             */
            if (this.yPanels.size() == 1) {
                varSelTabPane.removeTabAt(varSelTabPane.getTabCount() - 1);
                varSelTabPane.addTab("y", this.yPanels.get(0));
            }
        }

        /*
         * add the minus tab
         */
        if (this.yPanels.size() > 1) {
            varSelTabPane.addTab("-", this.delLastYPanel);
        }

        /*
         * and add the plus tab again
         */
        if (this.yPanels.size() < MAX_Y_PANELS) {
            varSelTabPane.addTab("+", this.newYPanel);
        }

        if (yPanel != null) {
            varSelTabPane.setSelectedComponent(yPanel);
        }
        validate();

    }

    private AllVarSelectionPanel createVariableSelectionPanel(VarSelectionMode varSelectionMode,
            TwissVariable defaultVariable) {

        SelectedVarManager selectedVarManager = new SelectedVarManagerImpl();
        selectedVarManager.setVarSelectionMode(varSelectionMode);

        AllVarSelectionPanel panel = createVariableSelectionPanel();
        panel.setSelectedVarManager(selectedVarManager);
        panel.setDefaultVariable(defaultVariable);
        panel.init();
        return panel;
    }

    /**
     * this method will be injected by spring to create a preconfigured variable-selection panel
     */
    protected abstract AllVarSelectionPanel createVariableSelectionPanel();

    /**
     * @param tfsDataSetManager the tfsDataSetManager to set
     */
    public void setTfsDataSetManager(TfsDataSetManager tfsDataSetManager) {
        this.tfsDataSetManager = tfsDataSetManager;
    }

    /**
     * @return the tfsDataSetManager
     */
    public TfsDataSetManager getTfsDataSetManager() {
        return tfsDataSetManager;
    }

    /*
     * methods from titlable and applyable
     */
    @Override
    public String getTitle() {
        return "Create new chart-view";
    }

    @Override
    public boolean apply() {
        /*
         * create the chart
         */
        Map<Integer, Collection<TwissVariable>> yVars = new HashMap<Integer, Collection<TwissVariable>>();

        for (int yAxis = 0; yAxis < yPanels.size(); yAxis++) {
            MadxVarSelector ySelector = this.yPanels.get(yAxis).getSelectedVarManager();
            yVars.put(yAxis, ySelector.getSelectedVariables());
        }

        tfsDataSetManager.createDataSets(txtName.getText(), xPanel.getSelectedVarManager().getSelectedVariable(),
                yVars, (TfsResultDataSetType) cboTfsDataSetType.getSelectedItem());

        return true;
    }

    @Override
    public void cancel() {
        /* do nothing */
    }
}
