/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author kfuchsbe
 */
@XStreamAlias("path-offsets")
public class ModelPathOffsetsImpl implements ModelPathOffsets {

    @XStreamAlias("repository-offset")
    private String repositoryOffset = null;

    @XStreamAlias("resource-offset")
    private String resourceOffset = null;

    @Override
    public String getRepositoryOffset() {
        return this.repositoryOffset;
    }

    @Override
    public String getResourceOffset() {
        return this.resourceOffset;
    }

    public void setResourceOffset(String resourceOffset) {
        this.resourceOffset = resourceOffset;
    }

    public void setRepositoryOffset(String repositoryOffset) {
        this.repositoryOffset = repositoryOffset;
    }

}
