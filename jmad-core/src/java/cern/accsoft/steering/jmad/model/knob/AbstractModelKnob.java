/**
 * 
 */
package cern.accsoft.steering.jmad.model.knob;

import cern.accsoft.steering.jmad.domain.knob.AbstractKnob;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * The simplest Knob, acting on a model
 * 
 * @author kfuchsbe
 */
public abstract class AbstractModelKnob extends AbstractKnob implements
        ModelKnob {

    /** the model to act on */
    private JMadModel model;

    /**
     * the default constructor, which enforces to provide a model.
     * 
     * @param model the model to use for the knob
     */
    public AbstractModelKnob(JMadModel model) {
        this.model = model;
    }

    /**
     * the constructor to allow also creation of the knob without setting the model immediately
     */
    public AbstractModelKnob() {
        /* model stays null and must be set afterwards! */
    }

    protected JMadModel getModel() {
        return model;
    }

    @Override
    public void setModel(JMadModel model) {
        this.model = model;
    }
}
