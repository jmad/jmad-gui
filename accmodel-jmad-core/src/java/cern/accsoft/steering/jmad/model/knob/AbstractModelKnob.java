// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
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
