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
/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.model.knob;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.AbstractKnob;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * Implementation of a Knob which allows to act on multiple Models. Changes to the Knob value have to be propagated to
 * all connected Models.
 * 
 * @author muellerg
 */
public abstract class AbstractMultiModelKnob extends AbstractKnob implements
        MultiModelKnob {

    /** the list of models that are connected to knob */
    private List<JMadModel> connectedModels;

    @Override
    public synchronized void addModel(JMadModel model) {
        this.connectedModels.add(model);
    }

    public AbstractMultiModelKnob() {
        /* new empty model list, models are added later on */
        this.connectedModels = new ArrayList<JMadModel>();
    }

    /**
     * @return the {@link JMadModel}s connected to this Knob.
     */
    public synchronized List<JMadModel> getConnectedModels() {
        return this.connectedModels;
    }
}
