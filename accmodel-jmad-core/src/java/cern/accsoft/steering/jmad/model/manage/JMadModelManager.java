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
 * $Id: ModelManager.java,v 1.2 2008-08-15 18:05:25 kfuchsbe Exp $
 * 
 * $Date: 2008-08-15 18:05:25 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.model.manage;

import java.util.List;

import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * this interface defines basic methods to keep track of the actual model. There is always exactly one active model.
 * <p>
 * The manager keeps also track of all created models in the service.
 * 
 * @author kfuchsbe
 */
public interface JMadModelManager {

    /**
     * @return the model, which is the actual one.
     */
    public JMadModel getActiveModel();

    /**
     * @param model the model to set as the active one.
     */
    public void setActiveModel(JMadModel model);

    /**
     * adds a model to the manager
     * 
     * @param model the model to add
     */
    public void addModel(JMadModel model);

    /**
     * remove a model from the manager
     * 
     * @param model the model to remove
     */
    public void removeModel(JMadModel model);

    /**
     * @return all the currently available models
     */
    public List<JMadModel> getModels();

    /**
     * adds a listener to the {@link JMadModelManager}
     * 
     * @param listener the listener to add
     */
    public void addListener(JMadModelManagerListener listener);

    /**
     * removes a listener from the {@link JMadModelManager}
     * 
     * @param listener the listener to remove
     */
    public void removeListener(JMadModelManagerListener listener);

    /**
     * cleans up each model. Does not remove anything from the Model-manager
     */
    public void cleanup();

}
