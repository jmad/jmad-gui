/**
 * 
 */
package cern.accsoft.steering.jmad.domain.machine;

import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;

/**
 * This the interface of a class that defines aperture-values
 * 
 * @author kfuchsbe
 */
public interface ApertureDefinition {

    /**
     * @return the file which defines the s-positions of the aperture-model
     */
    public ModelFile getIndexFile();

    /**
     * @return a list of files that really contain the aperture values
     */
    public List<ModelFile> getPartFiles();
}
