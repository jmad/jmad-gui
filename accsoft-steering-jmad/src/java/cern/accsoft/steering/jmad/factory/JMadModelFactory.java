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