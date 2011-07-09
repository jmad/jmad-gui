/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * a simple interface to create model definitions
 * 
 * @author kfuchsbe
 */
public interface ModelDefinitionFactory {

    /**
     * @return a new modelDefinition
     */
    public abstract JMadModelDefinition create();
}
