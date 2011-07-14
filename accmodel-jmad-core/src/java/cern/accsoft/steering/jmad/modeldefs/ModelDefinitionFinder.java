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
package cern.accsoft.steering.jmad.modeldefs;

import java.util.List;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This interface provides methods to search for available jmad model configurations.
 * 
 * @author kfuchsbe
 */
public interface ModelDefinitionFinder {

    /**
     * searches for all available model-definitions.
     * 
     * @return all available model definitions or an empty list, if none are available.
     */
    public List<JMadModelDefinition> findAllModelDefinitions();

}
