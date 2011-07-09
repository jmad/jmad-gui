/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;

/**
 * This interface represents the description of a file used for a model and provides the information where to find it.
 * 
 * @author kfuchsbe
 */
public interface ModelFile {

    /**
     * Where to search the file? In the repository (or if not found there in the repo-copy within the jar) or in the
     * sourcepath
     * 
     * @author kfuchsbe
     */
    public static enum ModelFileLocation {
        REPOSITORY("repdata") {
            @Override
            public String getPathOffset(ModelPathOffsets offsets) {
                return offsets.getRepositoryOffset();
            }
        },
        RESOURCE("resdata") {
            @Override
            public String getPathOffset(ModelPathOffsets offsets) {
                return offsets.getResourceOffset();
            }
        };

        /** The path prefix within the jar/zip/source */
        private String resourcePrefix;

        public abstract String getPathOffset(ModelPathOffsets offsets);

        private ModelFileLocation(String resourcePrefix) {
            this.resourcePrefix = resourcePrefix;
        }

        public String getResourcePrefix() {
            return resourcePrefix;
        }
    }

    
    /**
     * @return the name used by the {@link ModelFileFinder} to find the file.
     */
    public abstract String getName();

    /**
     * @return the location where to search for the file.
     */
    public abstract ModelFileLocation getLocation();

}
