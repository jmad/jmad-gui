/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.JMadModelStartupConfiguration;
import cern.accsoft.steering.jmad.modeldefs.ModelManager;
import cern.accsoft.steering.jmad.modeldefs.domain.OpticsDefinition;
import cern.accsoft.steering.jmad.service.JMadService;

/**
 * The class with common methods for all {@link ModelManager}s
 * 
 * @author ${user}
 * @version $Revision$, $Date$, $Author$
 */
public abstract class AbstractModelManager implements ModelManager {

    /** The logger for the class */
    static final Logger LOGGER = Logger.getLogger(AbstractModelManager.class);

    /** the possibly spring injected start up configuration for the jmad models */
    private JMadModelStartupConfiguration startupConfiguration = null;
    
    /** The service which provides all jmad functionality */
    private JMadService jmadService;

    /**
     * cleans up all models. This will be called by spring when the bean dies.
     */
    public void cleanup() {
        for (JMadModel model : getModels()) {
            try {
                this.getJMadService().deleteModel(model);
                model.cleanup();
            } catch (JMadModelException e) {
                LOGGER.error("Error during model [" + model + "] cleanup.", e);
            }
        }
    }

    @Override
    public void resetModels() {
        for (JMadModel model : getModels()) {
            try {
                model.reset();
            } catch (JMadModelException e) {
                LOGGER.error("Error during model [" + model + "] reset.", e);
            }
        }
        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Reset of models done!");
        }
    }

    @Override
    public Collection<String> availableCommonOpticNames() {
        List<String> opticNames = new ArrayList<String>();
        boolean firstModel = true;
        for (JMadModel model : getModels()) {
            if (firstModel) {
                opticNames.addAll(this.extractAvailableOpticNames(model));
                firstModel = false;
                continue;
            }

            /* now we only keep optic names that are defined in both lists */
            List<String> newOpticNames = new ArrayList<String>();
            for (String opticName : this.extractAvailableOpticNames(model)) {
                if (opticNames.contains(opticName)) {
                    newOpticNames.add(opticName);
                }
            }

            opticNames = newOpticNames;
        }

        return opticNames;
    }

    private List<String> extractAvailableOpticNames(JMadModel model) {
        List<OpticsDefinition> opticDefinitions = model.getModelDefinition().getOpticsDefinitions();
        List<String> opticNames = new ArrayList<String>();
        for (OpticsDefinition opticsDefinition : opticDefinitions) {
            opticNames.add(opticsDefinition.getName());
        }

        return opticNames;
    }

    @Override
    public void setActiveOptics(String opticName) {
        for (JMadModel model : getModels()) {
            String activeOpticName = null;
            if (model.getActiveOpticsDefinition() != null
                    && (activeOpticName = model.getActiveOpticsDefinition().getName()).equals(opticName)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("OpticDefinition [" + opticName + "] already loaded in Model [" + model.getName()
                            + "]");
                }
                continue;
            }

            if (this.extractAvailableOpticNames(model).contains(opticName)) {
                OpticsDefinition opticsDefinition = model.getModelDefinition().getOpticsDefinition(opticName);
                try {
                    model.setActiveOpticsDefinition(opticsDefinition);
                    continue;
                } catch (JMadModelException e) {
                    LOGGER.error("Failed setting Optic [" + opticsDefinition.getName() + "] in Model ["
                            + model.getName() + "]", e);
                }
            } else {
                LOGGER.error("Optic [" + opticName + "] not defined for Model [" + model.getName() + "]");
            }

            LOGGER.warn("Model will stay at Optic [" + activeOpticName + "]");
        }
    }

    public void setJmadService(JMadService jmadService) {
        this.jmadService = jmadService;
    }

    @Override
    public JMadService getJMadService() {
        return jmadService;
    }

    @Override
    public void setStartupConfiguration(JMadModelStartupConfiguration startupConfiguration) {
        this.startupConfiguration = startupConfiguration;
    }

    @Override
    public JMadModelStartupConfiguration getStartupConfiguration() {
        return startupConfiguration;
    }

}