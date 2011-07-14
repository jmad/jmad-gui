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
