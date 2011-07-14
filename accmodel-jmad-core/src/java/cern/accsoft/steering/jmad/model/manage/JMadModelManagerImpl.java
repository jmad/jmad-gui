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
 * $Id: DefaultModelManager.java,v 1.2 2008-08-15 18:05:25 kfuchsbe Exp $
 * 
 * $Date: 2008-08-15 18:05:25 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.model.manage;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * simplest possible implementation of a {@link JMadModelManager}.
 * 
 * @author kfuchsbe
 */
public class JMadModelManagerImpl implements JMadModelManager {

    /** The logger for the class */
    private final static Logger LOGGER = Logger.getLogger(JMadModelManagerImpl.class);

    /** the active model */
    private JMadModel activeModel = null;

    /** all the listeners */
    private final List<JMadModelManagerListener> listeners = new ArrayList<JMadModelManagerListener>();

    /** all the available models */
    private final List<JMadModel> models = new ArrayList<JMadModel>();

    //
    // methods for interface ModelManager
    //

    @Override
    public final JMadModel getActiveModel() {
        return activeModel;
    }

    @Override
    public final void setActiveModel(JMadModel model) {
        this.activeModel = model;
        fireChangedActiveModel();
    }

    @Override
    public final void addListener(JMadModelManagerListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public final void removeListener(JMadModelManagerListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * informs all listeners that the active model has changed.
     */
    protected final void fireChangedActiveModel() {
        for (JMadModelManagerListener listener : listeners) {
            listener.changedActiveModel(this.activeModel);
        }
    }

    /**
     * notifies all listeners that a model has been added
     * 
     * @param model the newly added model
     */
    private void fireAddedModel(JMadModel model) {
        for (JMadModelManagerListener listener : this.listeners) {
            listener.addedModel(model);
        }
    }

    /**
     * notify all listeners that a model was removed
     * 
     * @param model the model which was removed
     */
    private void fireRemovedModel(JMadModel model) {
        for (JMadModelManagerListener listener : this.listeners) {
            listener.removedModel(model);
        }
    }

    @Override
    public void addModel(JMadModel model) {
        if (!this.models.contains(model)) {
            this.models.add(model);
            fireAddedModel(model);
        }
    }

    @Override
    public List<JMadModel> getModels() {
        return this.models;
    }

    @Override
    public void removeModel(JMadModel model) {
        if ((this.activeModel != null) && (this.activeModel.equals(model))) {
            setActiveModel(null);
        }
        if (this.models.contains(model)) {
            this.models.remove(model);
            fireRemovedModel(model);
        }
    }

    @Override
    public void cleanup() {
        for (JMadModel model : getModels()) {
            try {
                model.cleanup();
            } catch (JMadModelException e) {
                LOGGER.error("Error while cleaning up model.", e);
            }
        }
    }

}
