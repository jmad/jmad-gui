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
