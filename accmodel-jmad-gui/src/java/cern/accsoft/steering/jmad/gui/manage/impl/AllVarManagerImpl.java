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
