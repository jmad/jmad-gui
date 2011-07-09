/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.domain;

import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelFileDependant;

/**
 * This is the interface for definitions of optics.
 * 
 * @author kfuchsbe
 */
public interface OpticsDefinition extends ModelFileDependant {

    /**
     * @return the name of the optics
     */
    public abstract String getName();

    /**
     * @return the {@link ModelFile}s to call for initializing this optics
     */
    public abstract List<ModelFile> getInitFiles();

    /**
     * @return a list of model files that must be called after switching to ptc
     */
    public abstract List<ModelFile> getPostPtcUniverseFiles();

    /**
     * convenience methods to get all the relative pathnames of the required files
     * 
     * @return a list of FileNames for all ModelFiles this Optic consists of
     */
    public abstract String[] getOpticFileNames();

    /**
     * @return <code>true</code> if this optics can be loaded together with another optics. <code>false</code> if it is
     *         a full optics definition which in most cases should override all strengths.
     */
    public abstract boolean isOverlay();
}
