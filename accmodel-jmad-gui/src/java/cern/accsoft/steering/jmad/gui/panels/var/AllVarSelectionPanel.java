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

package cern.accsoft.steering.jmad.gui.panels.var;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTabbedPane;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * This is just a panel that collects all available var selection panels
 * 
 * @author kaifox
 */
public class AllVarSelectionPanel extends JTabbedPane {
    private static final long serialVersionUID = 1447914858470855189L;

    /**
     * all the panels that shall be displayed as tabs. The key shall be the name of the tab and the
     */
    private Map<String, VarSelectionPanel> panels = new HashMap<String, VarSelectionPanel>();

    /**
     * the var selection manager which will be propagated to all the children
     */
    private SelectedVarManager selectedVarManager = new SelectedVarManagerImpl();

    /**
     * the variable which shall be selected by default in all the panels, if available there.
     */
    private TwissVariable defaultVariable = null;

    /**
     * the init method which must be called after setting the SelectedVarManager correctly.
     */
    public void init() {
        initComponents();
    }

    /**
     * puts all the panels in a tab-panel and displays it
     */
    private void initComponents() {

        for (String key : getPanels().keySet()) {

            /*
             * initialize the panels
             */
            VarSelectionPanel panel = getPanels().get(key);
            panel.setSelectedVarManager(this.getSelectedVarManager());
            panel.setDefaultVariable(getDefaultVariable());
            panel.init();

            /*
             * and put them as tabs
             */
            addTab(key, panel);
        }
    }

    /*
     * getters and setters
     */

    public void setPanels(Map<String, VarSelectionPanel> panels) {
        this.panels = panels;
    }

    public Map<String, VarSelectionPanel> getPanels() {
        return panels;
    }

    public void setDefaultVariable(TwissVariable defaultVariable) {
        this.defaultVariable = defaultVariable;
    }

    public TwissVariable getDefaultVariable() {
        return defaultVariable;
    }

    public void setSelectedVarManager(SelectedVarManager selectedVarManager) {
        this.selectedVarManager = selectedVarManager;
    }

    public SelectedVarManager getSelectedVarManager() {
        return selectedVarManager;
    }

}
