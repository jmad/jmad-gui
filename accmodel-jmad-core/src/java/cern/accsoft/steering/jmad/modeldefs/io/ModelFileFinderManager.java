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
package cern.accsoft.steering.jmad.modeldefs.io;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * this interface provides lookup methods for modelFileFinders
 * 
 * @author kfuchsbe
 */
public interface ModelFileFinderManager {

    /**
     * return a modelFile finder for a modeldefinition
     * 
     * @param modelDefinition the model definition for which to get the filefinder
     * @return the ModelFileFinder
     */
    public ModelFileFinder getModelFileFinder(JMadModelDefinition modelDefinition);
}
