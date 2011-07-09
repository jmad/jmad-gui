package cern.accsoft.steering.jmad.model;

import cern.accsoft.steering.jmad.domain.machine.Range;

public interface JMadModelListener {

    /**
     * fired, when some values were changed, so that the calculated values are no longer valid.
     */
    public void becameDirty();

    /**
     * fired, when another range was selected in the model
     * 
     * @param newRange the new Range
     */
    public void rangeChanged(Range newRange);

    /**
     * fired when the elements Changed
     */
    public void elementsChanged();

    /**
     * fired, when the optics-values changed (are reloaded)
     */
    public void opticsChanged();

    /**
     * fired, when the optics definition was changed.
     */
    public void opticsDefinitionChanged();

}
