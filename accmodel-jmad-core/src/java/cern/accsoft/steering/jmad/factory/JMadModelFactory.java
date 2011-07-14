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
package cern.accsoft.steering.jmad.factory;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public interface JMadModelFactory {

    /**
     * creates a jmad model for the given {@link JMadModelDefinition} and configures it.
     * 
     * @param modelDefinition the {@link JMadModelDefinition} for which to create a model.
     * @return a fully configured jmad model.
     */
    public abstract JMadModel createModel(JMadModelDefinition modelDefinition);

    /**
     * adds the default knobs to the model
     * 
     * @param model the model to configure
     */
    public abstract void createDefaultKnobs(JMadModel model);

}
