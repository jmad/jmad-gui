package cern.accsoft.steering.jmad.modeldefs.io;

import java.io.File;
import java.io.InputStream;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.modeldefs.io.impl.ModelDefinitionUtil;

public interface ModelFileFinder {

    /**
     * returns an accessible file that is searched according to the given {@link ModelFile}.
     * 
     * @param modelFile the instance of {@link ModelFile} which describes where to find the file
     * @return the file
     */
    public abstract File getFile(ModelFile modelFile);

    /**
     * returns the content of the model file as input stream. This is useful e.g. for copying the file or for packing it
     * into a zip archive.
     * 
     * @param modelFile the model file for which to get the stream
     * @return the input stream
     */
    public abstract InputStream getStream(ModelFile modelFile);

    /**
     * puts together the path within the repository
     * 
     * @param modelFile the model file for which to put together the path
     * @return the absolute path to the file
     */
    public abstract String getRepositoryPath(ModelFile modelFile);

    /**
     * puts together the resource path for the file
     * 
     * @param modelFile the {@link ModelFile} for which to put together the resource path
     * @return the relative path used in jars below {@link ModelDefinitionUtil#PACKAGE_OFFSET} and in zip archives.
     */
    public abstract String getArchivePath(ModelFile modelFile);

    /**
     * set the priority mode for searching repository files.
     * 
     * @param priority the new priority
     */
    public void setRepositoryFilePriority(RepositoryFilePriority priority);

    /**
     * @return the actual priority mode for searching repository files.
     */
    public RepositoryFilePriority getRepositoryFilePriority();

    /**
     * This enum defines what shall take priority if dealing with repository files.
     * 
     * @author kfuchsbe
     */
    public static enum RepositoryFilePriority {
        /**
         * when the priority is set to Archive, then a repository file is first searched in the archive (jar, zip) and
         * only if it cannot be extracted then it is looked up in the real repository.
         */
        ARCHIVE,
        /**
         * when this is used, then a repository file is first looked up in the repository path. Only if it can not be
         * found there then it is extracted from the archive.
         */
        REPOSITORY;
    }

}