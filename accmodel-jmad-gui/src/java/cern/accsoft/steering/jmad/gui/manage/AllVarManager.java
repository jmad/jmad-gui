package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;


/**
 * provides a list of all possibly selectable variables.
 * 
 * @author kaifox
 *
 */
public interface AllVarManager {
    
    /**
     * @return all selectable twiss variables
     */
    public List<TwissVariable> getAllSelectableVariables();
}
