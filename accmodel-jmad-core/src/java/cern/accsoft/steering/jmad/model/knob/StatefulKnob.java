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
 * A {@link Knob} which is sensitive to fundamental changes in the model e.g. loading of Optics.
 * 
 * @author muellerg
 */
public interface StatefulKnob extends Knob {

    /**
     * Write the current state of the Knob to the model given. E.g. for a linear knob that would trigger a calculate
     * knob strength values from total knob value and write to the model provided.
     * 
     * @param model the {@link JMadModel} to write the {@link Knob}s state to.
     */
    public abstract void writeCurrentStateToModel(JMadModel model);
}
