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
