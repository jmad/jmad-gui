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
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * the simplest implementation of a class collecting variables.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class SelectedVarManagerImpl implements SelectedVarManager {

    /** The logger for the class */
    private final static Logger logger = Logger.getLogger(SelectedVarManagerImpl.class);

    /** how many variables may be selected */
    private VarSelectionMode varSelectionMode = VarSelectionMode.MULTIPLE;

    /** the variables */
    private List<TwissVariable> variables = new ArrayList<TwissVariable>();

    @Override
    public void add(TwissVariable twissVariable) {
        if (VarSelectionMode.SINGLE == this.varSelectionMode) {
            this.clear();
        }
        this.variables.add(twissVariable);
    }

    @Override
    public void clear() {
        this.variables.clear();
    }

    @Override
    public Collection<TwissVariable> getSelectedVariables() {
        return this.variables;
    }

    @Override
    public void remove(TwissVariable twissVariable) {
        if (VarSelectionMode.SINGLE == this.varSelectionMode) {
            return;
        }
        this.variables.remove(twissVariable);
    }

    @Override
    public void setVarSelectionMode(VarSelectionMode varSelectionMode) {
        this.varSelectionMode = varSelectionMode;
    }

    @Override
    public TwissVariable getSelectedVariable() {
        if (VarSelectionMode.SINGLE != this.varSelectionMode) {
            logger.warn("This only makes sense in single selection mode!");
            return null;
        }
        if (variables.size() == 1) {
            return variables.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VarSelectionMode getVarSelectionMode() {
        return this.varSelectionMode;
    }

}
