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
package cern.accsoft.steering.jmad.model;

import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.KnobType;

/**
 * This is the interface of a class, which manages several knobs
 * 
 * @author kfuchsbe
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
