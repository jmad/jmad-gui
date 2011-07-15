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
