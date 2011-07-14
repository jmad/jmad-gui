// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
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
