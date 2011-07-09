package cern.accsoft.steering.jmad.modeldefs.lhc;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.ModelManager;
import cern.accsoft.steering.util.acc.BeamNumber;

/**
 * The LHC specific model manager.
 * 
 * @author muellerg
 */
public interface LhcModelManager extends ModelManager {

    /**
     * returns the model instance for the given beam.
     * 
     * @param beam the beam for which to return the model
     * @return the model instance
     */
    public JMadModel getModel(BeamNumber beam);

    /**
     * Load model for the model definition specified by the given name. Nothing will be done, if the model definition
     * with the given name does not exist.
     * 
     * @param modelDefinitionName the name of the model definition to load the models for.
     * @throws JMadModelException in case something went wrong during the model initialization
     */
    public void loadModelDefinition(String modelDefinitionName) throws JMadModelException;
}
