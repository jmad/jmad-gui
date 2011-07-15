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
package cern.accsoft.steering.jmad.model.knob.custom;

import cern.accsoft.steering.jmad.domain.knob.KnobType;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.knob.AbstractModelKnob;

/**
 * This knob allows setting the x,y,px,py initial conditions according to the given deltap. This enforces the trajectory
 * to match correctly the dispersion orbit.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class DeltaPKnob extends AbstractModelKnob {

    public DeltaPKnob(JMadModel model) {
        super(model);
    }

    @Override
    public String getName() {
        return "deltap";
    }

    @Override
    protected void doSetTotalValue(double value) {
        if (getModel().getTwissInitialConditions().getDeltap() == null) {
            return;
        }
        double deltap = value;
        TwissInitialConditions twiss = getModel().getTwissInitialConditions();

        twiss.setDeltap(deltap);
        twiss.setX(twiss.getDx() * deltap);
        twiss.setY(twiss.getDy() * deltap);
        twiss.setPx(twiss.getDpx() * deltap);
        twiss.setPy(twiss.getDpy() * deltap);
    }

    @Override
    public double getTotalValue() {
        if (getModel().getTwissInitialConditions().getDeltap() == null) {
            return 0.0;
        } else {
            return getModel().getTwissInitialConditions().getDeltap();
        }
    }

    @Override
    public String getKey() {
        return this.getName().toLowerCase();
    }

    @Override
    public KnobType getType() {
        return KnobType.CUSTOM;
    }

    @Override
    public String getDescription() {
        return "Modifies consistently initial conditions of x and y dependent on deltap/p";
    }

}
