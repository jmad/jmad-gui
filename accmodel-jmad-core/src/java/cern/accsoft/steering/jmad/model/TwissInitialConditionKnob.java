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

import cern.accsoft.steering.jmad.domain.knob.KnobType;
import cern.accsoft.steering.jmad.domain.knob.bean.BeanPropertyKnob;

/**
 * this is a knob for a twiss initial condition.
 * 
 * @author kfuchsbe
 */
public class TwissInitialConditionKnob extends BeanPropertyKnob {

    private final JMadModel model;

    public TwissInitialConditionKnob(JMadModel model, String propertyName) {
        super(propertyName);
        this.model = model;
    }

    @Override
    public KnobType getType() {
        return getKnobType();
    }

    public static final KnobType getKnobType() {
        return KnobType.TWISS_INITIAL_CONDITION;
    }

    @Override
    protected Object getBean() {
        return this.model.getTwissInitialConditions();
    }

    @Override
    public String getDescription() {
        return null;
    }
}
