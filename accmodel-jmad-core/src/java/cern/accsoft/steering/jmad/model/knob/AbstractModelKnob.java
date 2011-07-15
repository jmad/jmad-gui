// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

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
public abstract class AbstractModelKnob extends AbstractKnob implements ModelKnob {

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
