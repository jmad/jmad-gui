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
