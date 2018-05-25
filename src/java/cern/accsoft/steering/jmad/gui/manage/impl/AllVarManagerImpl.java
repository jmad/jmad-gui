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

package cern.accsoft.steering.jmad.gui.manage.impl;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;
import cern.accsoft.steering.jmad.gui.manage.AllVarManager;
import cern.accsoft.steering.jmad.gui.manage.VariableProvider;

public class AllVarManagerImpl implements AllVarManager {

    private List<TwissVariable> allVariables = new ArrayList<TwissVariable>();

    private List<VariableProvider> variableProviders = new ArrayList<VariableProvider>();

    public void init() {
        this.allVariables.clear();
        for (VariableProvider provider : getVariableProviders()) {
            this.allVariables.addAll(provider.getAvailableVariables());
        }
    }

    @Override
    public List<TwissVariable> getAllSelectableVariables() {
        return this.allVariables;
    }

    public void setVariableProviders(List<VariableProvider> variableProviders) {
        this.variableProviders = variableProviders;
    }

    private List<VariableProvider> getVariableProviders() {
        return variableProviders;
    }

}
