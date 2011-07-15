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

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * This is the interface of a class that collects selected variables for one axis.
 * 
 * @author kaifox
 */
public interface SelectedVarManager extends MadxVarSelector {

    /**
     * adds a new variable
     * 
     * @param twissVariable the variable to add
     */
    public void add(TwissVariable twissVariable);

    /**
     * remove the given variable
     * 
     * @param twissVariable the variable to remove
     */
    public void remove(TwissVariable twissVariable);

    /**
     * clears the variables
     */
    public void clear();

    /**
     * defines, if one or more variables may be selected.
     * 
     * @param varSelectionMode
     */
    public void setVarSelectionMode(VarSelectionMode varSelectionMode);

    /**
     * @return the actual selection mode
     */
    public VarSelectionMode getVarSelectionMode();

    /**
     * this mode represents, if only one variable can be selected, or multiple ones.
     * 
     * @author kaifox
     */
    public static enum VarSelectionMode {
        SINGLE, MULTIPLE;
    }
}
