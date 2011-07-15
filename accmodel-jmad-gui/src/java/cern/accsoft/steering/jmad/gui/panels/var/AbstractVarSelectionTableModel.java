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

import javax.swing.table.AbstractTableModel;

import cern.accsoft.steering.jmad.gui.manage.VariableProvider;

public abstract class AbstractVarSelectionTableModel extends AbstractTableModel implements VariableProvider {
    private static final long serialVersionUID = -2384256307499536767L;

    /** the variables selected in the table */
    private SelectedVarManager selectedVarManager = new SelectedVarManagerImpl();

    private boolean showSelectColumn;

    protected int axesCount = 0;

    protected boolean isShowSelectColumn() {
        return showSelectColumn;
    }

    public void setShowSelectColumn(boolean showSelectColumn) {
        this.showSelectColumn = showSelectColumn;
        if (this.showSelectColumn) {
            axesCount = 1;
        } else {
            axesCount = 0;
        }
        updateColumnCounts();
    }

    protected abstract void updateColumnCounts();

    public void setSelectedVarManager(SelectedVarManager selectedVarManager) {
        this.selectedVarManager = selectedVarManager;
    }

    protected SelectedVarManager getSelectedVarManager() {
        return selectedVarManager;
    }
}