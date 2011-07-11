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
 * a knob which needs a model
 * 
 * @author muellerg
 */
public interface ModelKnob extends Knob {

    /**
     * set the model to operate on
     * 
     * @param model the model to set
     */
    public void setModel(JMadModel model);
}