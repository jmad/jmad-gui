/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.model.knob;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * Knob which can be attached to multiple {@link JMadModel}s. Changes to the Knob values will then be written to all
 * Models.
 * 
 * @author muellerg
 */
public interface MultiModelKnob extends Knob {

    /**
     * Add a {@link JMadModel} to the Knob. Every added Model will be updated with knob value changes.
     * 
     * @param model the {@link JMadModel} to add to the Knob
     */
    public abstract void addModel(JMadModel model);
}
