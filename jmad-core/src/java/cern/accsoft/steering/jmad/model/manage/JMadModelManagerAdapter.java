/**
 * 
 */
package cern.accsoft.steering.jmad.model.manage;

import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * A simple adapter which implements all methods of the listener and requires only those to be implemented when needed.
 * 
 * @author kfuchsbe
 */
public class JMadModelManagerAdapter implements JMadModelManagerListener {

    @Override
    public void addedModel(JMadModel newModel) {
        /* override if needed */
    }

    @Override
    public void changedActiveModel(JMadModel newActiveModel) {
        /* override if needed */
    }

    @Override
    public void removedModel(JMadModel removedModel) {
        /* override if needed */
    }

}
