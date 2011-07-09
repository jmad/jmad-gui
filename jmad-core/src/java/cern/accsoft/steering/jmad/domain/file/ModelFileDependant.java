/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

import java.util.Collection;

/**
 * This interface must be implemented by all parts of model definitions that require modelfiles. This is necessary
 * because by this interface the file finder decides which files are required and therefore need to be unpacked/copied.
 * 
 * @author kfuchsbe
 */
public interface ModelFileDependant {

    /**
     * @return all the {@link ModelFile}s that are required by this definition-component.
     */
    public Collection<ModelFile> getRequiredFiles();
}
