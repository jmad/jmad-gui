package cern.accsoft.steering.jmad.domain.file;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class AbstractModelFile implements ModelFile {

    /** The model file location we use when the location is null after reading. */
    private static final ModelFileLocation DEFAULT_MODEL_FILE_LOCATION = ModelFileLocation.REPOSITORY;
    
    /** The location of the file, which is the repository by default. */
    @XStreamAlias("location")
    @XStreamAsAttribute
    private ModelFileLocation location = DEFAULT_MODEL_FILE_LOCATION;
    
    /** The relative path of the file */
    @XStreamAlias("path")
    @XStreamAsAttribute
    private String path = null;

    /**
     * the constructor with the name only. (location stays to default)
     * 
     * @param path the relative path of the file
     */
    public AbstractModelFile(String path) {
        this.path = path;
    }

    /**
     * The constructor with filename and location
     * 
     * @param path the relative path of the model file
     * @param location the location (repository or resources) where to search for the file
     */
    public AbstractModelFile(String path, ModelFileLocation location) {
        this(path);
        this.location = location;
    }

    @Override
    public ModelFileLocation getLocation() {
        return this.location;
    }

    @Override
    public String getName() {
        return this.path;
    }

    @Override
    public CallableModelFileImpl clone() throws CloneNotSupportedException {
        return (CallableModelFileImpl) super.clone();
    }

    /**
     * is called just before serialization. Removes some information so that the default values are not written to xml
     */
    protected void fillWriteReplace(AbstractModelFile writtenObj) {
        if (DEFAULT_MODEL_FILE_LOCATION == this.location) {
            writtenObj.location = null;
        }
    }

    /**
     * is called just after deserialization. It configures the object with default values, if none were stored in the
     * serialized version.
     * 
     */
    protected void abstractReadResolve() {
        if (location == null) {
            location = DEFAULT_MODEL_FILE_LOCATION;
        }
    }

}