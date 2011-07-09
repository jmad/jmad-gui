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
