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
