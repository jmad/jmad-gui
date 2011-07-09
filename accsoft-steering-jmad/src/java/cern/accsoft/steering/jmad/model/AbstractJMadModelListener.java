/**
 * 
 */
package cern.accsoft.steering.jmad.model;

import cern.accsoft.steering.jmad.domain.machine.Range;

/**
 * A model-listener, which implements all the methods, which may be overridden by subclasses, in order to react only on
 * special events.
 * 
 * @author kfuchsbe
 */
public class AbstractJMadModelListener implements JMadModelListener {

    @Override
    public void becameDirty() {
        /* to be overridden if required */
    }

    @Override
    public void elementsChanged() {
        /* to be overridden if required */
    }

    @Override
    public void opticsChanged() {
        /* to be overridden if required */
    }

    @Override
    public void opticsDefinitionChanged() {
        /* to be overridden if required */
    }

    @Override
    public void rangeChanged(Range newRange) {
        /* to be overridden if required */
    }

}
