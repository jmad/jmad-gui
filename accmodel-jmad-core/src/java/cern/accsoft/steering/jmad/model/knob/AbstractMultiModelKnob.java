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
public abstract class AbstractMultiModelKnob extends AbstractKnob implements MultiModelKnob {

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
