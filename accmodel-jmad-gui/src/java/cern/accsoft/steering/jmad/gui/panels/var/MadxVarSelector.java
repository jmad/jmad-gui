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

import java.util.Collection;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * The interface of a class that can select variables for plotting.
 * 
 * @author kaifox
 */
public interface MadxVarSelector {

    /**
     * This method has to return all the selected variables
     * 
     * @return all the selected variables.
     */
    public Collection<TwissVariable> getSelectedVariables();

    /**
     * This is a convenient method to get the selected variable when in single-variable selection mode.
     * 
     * @return returns the selected variable.
     */
    public TwissVariable getSelectedVariable();

}
