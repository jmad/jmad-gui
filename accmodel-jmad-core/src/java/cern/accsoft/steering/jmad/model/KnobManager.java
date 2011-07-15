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
package cern.accsoft.steering.jmad.model;

import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.KnobType;

/**
 * This is the interface of a class, which manages several knobs
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface KnobManager {

    /**
     * @return all available custom knobs
     */
    public List<Knob> getCustomKnobs();

    /**
     * @param knob the knob to add
     */
    public void addCustomKnob(Knob knob);

    /**
     * removes all the knobs, except the custom ones.
     */
    public void cleanup();

    /**
     * retrieves a knob given by the type and the key within the type
     * 
     * @param type the type of the knob
     * @param key the unique key within the type
     * @return the knob
     */
    public Knob getKnob(KnobType type, String key);

}
