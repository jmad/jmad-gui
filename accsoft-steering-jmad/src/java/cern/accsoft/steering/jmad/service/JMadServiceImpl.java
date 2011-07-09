/**
 * 
 */
package cern.accsoft.steering.jmad.service;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.factory.JMadModelFactory;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.model.manage.JMadModelManager;
import cern.accsoft.steering.jmad.modeldefs.JMadModelDefinitionManager;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionExporter;
import cern.accsoft.steering.jmad.modeldefs.io.JMadModelDefinitionImporter;
import cern.accsoft.steering.jmad.util.JMadPreferences;

/**
 * The default implementation of the jmad-service. This class is configured by spring.
 * 
 * @author kfuchsbe
 */
public class JMadServiceImpl implements JMadService {

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(JMadServiceImpl.class);

    /** The preferences, injected by spring */
    private JMadPreferences preferences;

    /** The model factory to use */
    private JMadModelFactory modelFactory;

    /** The model definition manager to use */
    private JMadModelDefinitionManager modelDefinitionManager;

    /** The model definition exporter */
    private JMadModelDefinitionExporter modelDefinitionExporter;

    /** The class to import model definitions */
    private JMadModelDefinitionImporter modelDefinitionImporter;

    /** The manager for the models */
    private JMadModelManager modelManager;

    /*
     * methods of interface JMadService
     */

    @Override
    public JMadModel createModel(JMadModelDefinition definition) {
        JMadModel model = getModelFactory().createModel(definition);
        if ((model != null) && (getModelManager() != null)) {
            getModelManager().addModel(model);
        }
        return model;
    }

    @Override
    public JMadPreferences getPreferences() {
        if (this.preferences == null) {
            LOGGER.warn("Preferences not set. Maybe config error?");
        }
        return this.preferences;
    }

    @Override
    public JMadModelDefinitionManager getModelDefinitionManager() {
        if (this.modelDefinitionManager == null) {
            LOGGER.warn("ModelDefinitionManager not set. Maybe config error.");
        }
        return modelDefinitionManager;
    }

    /*
     * Getters and setters used for spring
     */

    public void setPreferences(JMadPreferences preferences) {
        this.preferences = preferences;
    }

    public void setModelFactory(JMadModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    private JMadModelFactory getModelFactory() {
        if (this.modelFactory == null) {
            LOGGER.warn("ModelFactory not set. Maybe config error.");
        }
        return modelFactory;
    }

    public void setModelDefinitionManager(JMadModelDefinitionManager modelDefinitionManager) {
        this.modelDefinitionManager = modelDefinitionManager;
    }

    @Override
    public JMadModelManager getModelManager() {
        if (this.modelManager == null) {
            LOGGER.warn("ModelManager not set. Maybe config error.");
        }
        return this.modelManager;
    }

    public void setModelManager(JMadModelManager modelManager) {
        this.modelManager = modelManager;
    }

    @Override
    public JMadModelDefinitionExporter getModelDefinitionExporter() {
        if (this.modelDefinitionExporter == null) {
            LOGGER.warn("ModelDefinitionExporter not set. Maybe config error.");
        }
        return this.modelDefinitionExporter;
    }

    public void setModelDefinitionExporter(JMadModelDefinitionExporter modelDefinitionExporter) {
        this.modelDefinitionExporter = modelDefinitionExporter;
    }

    public void setModelDefinitionImporter(JMadModelDefinitionImporter modelDefinitionImporter) {
        this.modelDefinitionImporter = modelDefinitionImporter;
    }

    @Override
    public JMadModelDefinitionImporter getModelDefinitionImporter() {
        return modelDefinitionImporter;
    }

    @Override
    public void deleteModel(JMadModel model) {
        getModelManager().removeModel(model);
    }
}
