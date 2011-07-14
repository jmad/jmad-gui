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
package cern.accsoft.steering.jmad.service;

import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.modeldefs.JMadModelDefinitionManager;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionExporter;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionImporter;
import cern.accsoft.steering.jmad.util.JMadPreferences;

/**
 * This is the main entry-point to jmad. This interface offers methods to create new model, retrieve the active models
 * 
 * @author kfuchsbe
 */
public interface JMadService {

    /**
     * @return a class which knows about all the created models and also has one 'active' one which e.g. can be used for
     *         switching GUIs etc.
     */
    public JMadModelManager getModelManager();

    /**
     * @return a class which knows about all internally available model definitions
     */
    public JMadModelDefinitionManager getModelDefinitionManager();

    /**
     * @return a class to export model definitions
     */
    public JMadModelDefinitionExporter getModelDefinitionExporter();

    /**
     * @return a class to import model definitions
     */
    public JMadModelDefinitionImporter getModelDefinitionImporter();

    /**
     * The preferences which are visible by all the models. Setting parameters here changes the behavior of the models.
     * 
     * @return the actual preferences for JMad
     */
    public JMadPreferences getPreferences();

    /**
     * This method creates a model based on the given model definition.
     * 
     * @param definition the model definition from which to create a new model.
     * @return the new model
     */
    public JMadModel createModel(JMadModelDefinition definition);

    /**
     * removes the model from the list of available models. I.e. removes all internal references to this model.
     * 
     * @param model the model to remove
     */
    public void deleteModel(JMadModel model);

}
