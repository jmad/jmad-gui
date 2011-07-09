/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

/**
 * interface for defining the path offsets within repository and resources. this is valid for one model definition.
 * 
 * @author kfuchsbe
 */
public interface ModelPathOffsets {

    /**
     * this method must return the offset within the resource-path-tree.
     * 
     * @return the offset
     */
    public abstract String getResourceOffset();

    /**
     * this method must return the offset within the repository.
     * 
     * @return the offset
     */
    public abstract String getRepositoryOffset();

}
