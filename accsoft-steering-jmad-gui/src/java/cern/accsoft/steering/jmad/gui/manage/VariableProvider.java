package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

public interface VariableProvider {

    public abstract List<? extends TwissVariable> getAvailableVariables();
}
