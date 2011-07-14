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
import cern.accsoft.steering.jmad.model.JMadModelImpl;
import cern.accsoft.steering.jmad.model.KnobManager;
import cern.accsoft.steering.jmad.model.knob.custom.DeltaPKnob;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public abstract class JMadModelFactoryImpl implements JMadModelFactory {

    @Override
    public final JMadModel createModel(JMadModelDefinition modelDefinition) {
        JMadModelImpl model = createJMadModelImpl();
        model.setModelDefinition(modelDefinition);
        createDefaultKnobs(model);
        return model;
    }

    @Override
    public final void createDefaultKnobs(JMadModel model) {
        KnobManager knobManager = model.getKnobManager();
        knobManager.addCustomKnob(new DeltaPKnob(model));
    }

    /**
     * This method will be injected by spring in order to create a preconfigured model
     * 
     * @return the preinitialized instance of a model
     */
    protected abstract JMadModelImpl createJMadModelImpl();

}
