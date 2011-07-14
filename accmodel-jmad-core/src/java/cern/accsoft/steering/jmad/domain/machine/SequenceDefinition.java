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
package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;

/**
 * This class defines the attributes which define a sequence.
 * 
 * @author kfuchsbe
 */
public interface SequenceDefinition {

    /**
     * @return the name of the sequence
     */
    public String getName();

    /**
     * @return the range-definitions for this sequence
     */
    public List<RangeDefinition> getRangeDefinitions();

    /**
     * @return the rangeDefinition, which shall be selected by default
     */
    public RangeDefinition getDefaultRangeDefinition();

    /**
     * @return the beam to use for this sequence.
     */
    public Beam getBeam();

    /**
     * returns the range definition of the given name or null
     * 
     * @param name the name of the range definition to find
     * @return the range definition of the given name if available
     */
    public RangeDefinition getRangeDefinition(String name);
}
