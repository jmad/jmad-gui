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

package cern.accsoft.steering.jmad.gui.manage;

import java.io.File;

import cern.accsoft.steering.jmad.domain.var.custom.StrengthVarSet;

/**
 * This interface represents a manager, which keeps track of certain variables which can used in twiss-tables.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface StrengthVarManager {

    /**
     * Replaces the actual variables with the ones from the file.
     * 
     * @param file the file from which to load the variables.
     */
    public void load(File file);

    /**
     * @return all the actually available variables
     */
    public StrengthVarSet getStrengthVarSet();

    /**
     * add a listener
     * 
     * @param listener the listener to add
     */
    public void addListener(StrengthVarManagerListener listener);

    /**
     * remove a listener
     * 
     * @param listener the listener to remove
     */
    public void removeListener(StrengthVarManagerListener listener);

}
