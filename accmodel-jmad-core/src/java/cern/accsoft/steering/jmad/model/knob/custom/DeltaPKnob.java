// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
package cern.accsoft.steering.jmad.model.knob.custom;

import cern.accsoft.steering.jmad.domain.knob.KnobType;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.knob.AbstractModelKnob;

/**
 * This knob allows setting the x,y,px,py initial conditions according to the given deltap. This enforces the trajectory
 * to match correctly the dispersion orbit.
 * 
 * @author kfuchsbe
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
