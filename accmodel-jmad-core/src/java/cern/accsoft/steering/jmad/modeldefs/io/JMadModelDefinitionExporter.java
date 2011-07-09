/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io;

import java.io.File;

import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

/**
 * This is the interface of a class that can export jmad model-definitions to flat files or zip files.
 * 
 * @author kfuchsbe
 */
public interface JMadModelDefinitionExporter {

    /**
     * exports the model definition to the given path. If the path is a directory then it is exported as separated local
     * files, If is a filename then it is exported as a jmd.zip file.
     * 
     * @param modelDefinition the model definition to export.
     * @param exportPath the destination path
     * @return either the xml file to which the model definition was written if the export was to separate files or the
     *         zip file to which the whole model definition and files were written.
     */
    public abstract File export(JMadModelDefinition modelDefinition, File exportPath);

    /**
     * exports the model definition to separate files within the destination directory
     * 
     * @param modelDefinition the modelDefinition to export
     * @param destDir the destination directory
     * @return the xml file to which the model definition was written to
     */
    public abstract File exportAsFiles(JMadModelDefinition modelDefinition, File destDir);

    /**
     * exports the model definition to a zip file containing all the required files.
     * 
     * @param modelDefinition the {@link JMadModelDefinition} to export
     * @param zipFile the zip file which shall finally contain the model definitioin
     * @return the zip file to which the data was written (can be different since the default extension might have been
     *         added)
     */
    public abstract File exportAsZip(JMadModelDefinition modelDefinition, File zipFile);
}
