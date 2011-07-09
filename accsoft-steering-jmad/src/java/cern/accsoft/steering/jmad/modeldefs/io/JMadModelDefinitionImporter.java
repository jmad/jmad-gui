/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io;

import java.io.File;
import java.util.Collection;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This is the interface of a class which can import modelDefinitions from files.
 * 
 * @author kfuchsbe
 */
public interface JMadModelDefinitionImporter {

    /**
     * returns all model definitions contained in the given file. If this is an xml file then this gives clearly only
     * one. Nevertheless in a zip there might be multiple.
     * 
     * @param file the file from which to load the model definitions
     * @return the model definitions
     */
    public abstract Collection<JMadModelDefinition> importModelDefinitions(File file);

    /**
     * This is a convenience method an in principle a shortcut to the first occurence int
     * {@link #importModelDefinitions(File)}. If there are multiple definitions in the file then the result might be
     * random!
     * 
     * @param file the file from which to load the model definition
     * @return one model definition
     */
    public abstract JMadModelDefinition importModelDefinition(File file);
}
