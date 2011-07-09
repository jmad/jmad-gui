/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs;

import java.util.List;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This interface provides methods to search for available jmad model configurations.
 * 
 * @author kfuchsbe
 */
public interface ModelDefinitionFinder {

    /**
     * searches for all available model-definitions.
     * 
     * @return all available model definitions or an empty list, if none are available.
     */
    public List<JMadModelDefinition> findAllModelDefinitions();

}
