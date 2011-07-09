/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * this interface provides lookup methods for modelFileFinders
 * 
 * @author kfuchsbe
 */
public interface ModelFileFinderManager {

    /**
     * return a modelFile finder for a modeldefinition
     * 
     * @param modelDefinition the model definition for which to get the filefinder
     * @return the ModelFileFinder
     */
    public ModelFileFinder getModelFileFinder(JMadModelDefinition modelDefinition);
}
