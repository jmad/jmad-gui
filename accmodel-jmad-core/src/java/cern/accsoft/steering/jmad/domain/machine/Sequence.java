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
package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.beam.Beam;

public interface Sequence {

    /**
     * @return the name
     */
    public abstract String getName();

    /**
     * @return the available ranges of the sequence.
     */
    public abstract List<Range> getRanges();

    /**
     * @return the default range for this sequence
     */
    public abstract Range getDefaultRange();

    /**
     * @return the beam
     */
    public abstract Beam getBeam();

    /**
     * @return the defining SequenceDefinition
     */
    public abstract SequenceDefinition getSequenceDefinition();

}
