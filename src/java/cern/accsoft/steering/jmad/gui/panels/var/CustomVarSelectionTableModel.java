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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.domain.var.custom.CustomVariable;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.model.manage.JMadModelManagerAdapter;
import cern.accsoft.steering.jmad.model.manage.StrengthVarManagerListener;

/**
 * This panel allows to parse a variable-file and add these variables to the twiss results.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class CustomVarSelectionTableModel extends AbstractVarSelectionTableModel {

    private static final long serialVersionUID = 1630862227442352343L;

    private JMadModelManager modelManager;

    private int columnCount;
    {
        updateColumnCounts();
    }

    private final static int COL_NAME = 0;
    private final static int COL_EXPRESSION = 1;
    private final static int COL_COMMENT = 2;

    /** All the variables to display */
    private List<CustomVariable> variables = new ArrayList<CustomVariable>();

    StrengthVarManagerListener strengthVarListener = new StrengthVarManagerListener() {

        @Override
        public void changedData() {
            JMadModel model = modelManager.getActiveModel();
            if (model != null) {
                variables = model.getStrengthsAndVars().getVariables();
            } else {
                variables = Collections.emptyList();
            }
            fireTableDataChanged();
        }
    };

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public int getRowCount() {
        return variables.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        CustomVariable variable = variables.get(row);
        switch (col - axesCount) {
        case -1:
            return getSelectedVarManager().getSelectedVariables().contains(variable);
        case COL_NAME:
            return variable.getMadxName();
        case COL_EXPRESSION:
            return variable.getExpression();
        case COL_COMMENT:
            return variable.getComment();
        default:
            return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col - axesCount) {
        case -1:
            return Boolean.class;
        case COL_NAME:
        case COL_EXPRESSION:
        case COL_COMMENT:
            return String.class;
        default:
            return null;
        }
    }

    @Override
    public String getColumnName(int col) {
        switch (col - axesCount) {
        case -1:
            return "plot";
        case COL_NAME:
            return "variable";
        case COL_EXPRESSION:
            return "expression";
        case COL_COMMENT:
            return "comment";
        default:
            return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if ((axesCount > 0) && (col == 0)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        CustomVariable variable = variables.get(row);
        switch (col - axesCount) {
        case -1:
            if ((Boolean) value) {
                getSelectedVarManager().add(variable);
            } else {
                getSelectedVarManager().remove(variable);
            }
            fireTableDataChanged();
            break;
        }

    }

    @Override
    public List<? extends TwissVariable> getAvailableVariables() {
        return variables;
    }

    @Override
    protected void updateColumnCounts() {
        columnCount = axesCount + 3;
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
        modelManager.addListener(new JMadModelManagerAdapter() {
            @Override
            public void changedActiveModel(JMadModel newActiveModel) {
                if(newActiveModel == null) {
                    return;
                }
                newActiveModel.getStrengthVarManager().addListener(strengthVarListener);
            }
        });
    }

}
