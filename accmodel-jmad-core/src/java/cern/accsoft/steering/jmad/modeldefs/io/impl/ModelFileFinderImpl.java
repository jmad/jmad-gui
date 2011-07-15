// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.io.impl;

import static cern.accmodel.commons.util.ResourceUtil.prependPathOffset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsets;
import cern.accsoft.steering.jmad.domain.file.ModelPathOffsetsImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;
import cern.accsoft.steering.jmad.modeldefs.domain.SourceInformation;
import cern.accsoft.steering.jmad.modeldefs.domain.SourceInformation.SourceType;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;
import cern.accsoft.steering.jmad.util.JMadPreferences;
import cern.accsoft.steering.jmad.util.StreamUtil;
import cern.accsoft.steering.jmad.util.TempFileUtil;

/**
 * This is the implementation of a class that finds model-files.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ModelFileFinderImpl implements ModelFileFinder {

    /** the logger for the class */
    private static final Logger LOGGER = Logger.getLogger(ModelFileFinderImpl.class);

    /** The offsets for a specific model */
    private ModelPathOffsets modelPathOffsets = new ModelPathOffsetsImpl();

    /** The paths from which the model definition was loaded */
    private SourceInformation sourceInformation = null;

    /**
     * The default strategy to search for repository files is to take them from the archive.
     */
    private RepositoryFilePriority repositoryFilePriority = RepositoryFilePriority.ARCHIVE;

    /** The preferences to find the resource path */
    private JMadPreferences preferences;

    /** The file util to create the temp-dirs. */
    private TempFileUtil fileUtil;

    /**
     * the setter for the necessary offsets.
     * 
     * @param modelPathOffsets the offsets to use.
     */
    public void setModelFilePathOffsets(ModelPathOffsets modelPathOffsets) {
        this.modelPathOffsets = modelPathOffsets;
    }

    @Override
    public File getFile(ModelFile modelFile) {
        FileSource source = getFileSource(modelFile);
        File file = source.getFile(this, modelFile);
        if (file == null) {
            LOGGER.warn("Could not get model file '" + modelFile.getName() + "' from source '" + source + "'");
        }
        return file;
    }

    @Override
    public InputStream getStream(ModelFile modelFile) {
        FileSource source = getFileSource(modelFile);
        return source.getStream(this, modelFile);
    }

    /**
     * get the stream for a file from the archive (JAR or ZIP)
     * 
     * @param modelFile the model file for which to get the stream
     * @return the stream
     */
    private InputStream getArchiveStream(ModelFile modelFile) {
        SourceInformation sourceInfo = getSourceInformation();
        SourceType sourceType;
        if (sourceInfo == null) {
            sourceType = SourceType.JAR;
        } else {
            sourceType = sourceInfo.getSourceType();
        }

        String archivePath = getArchivePath(modelFile);
        if (SourceType.JAR == sourceType) {
            String path = prependPathOffset(archivePath, ModelDefinitionUtil.PACKAGE_OFFSET);
            return ModelDefinitionUtil.BASE_CLASS.getResourceAsStream(path);
        } else if (SourceType.ZIP == sourceType) {
            return getZipInputStream(getSourceInformation().getRootPath(), archivePath);
        } else if (SourceType.FILE == sourceType) {
            return getFileInputStream(getSourceInformation().getRootPath(), archivePath);
        } else {
            LOGGER.warn("Unhandled source type '" + sourceType + "'.");
            return null;
        }

    }

    private InputStream getFileInputStream(File baseDir, String relativePath) {
        File file = new File(baseDir.getAbsolutePath() + File.separator + relativePath);
        LOGGER.debug("Fetching file '" + file.getAbsolutePath() + "'");
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not open file '" + file.getAbsolutePath() + "'", e);
            return null;
        }
    }

    private InputStream getZipInputStream(File file, String entryName) {
        LOGGER.debug("Fetching file '" + entryName + "' from zip file '" + file.getAbsolutePath() + "'");
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(file);
        } catch (ZipException e) {
            LOGGER.error("Could not open zip file '" + file.getAbsolutePath() + "'");
            return null;
        } catch (IOException e) {
            LOGGER.error("Could not open zip file '" + file.getAbsolutePath() + "'");
            return null;
        }
        ZipEntry entry = zipFile.getEntry(entryName);
        try {
            return zipFile.getInputStream(entry);
        } catch (IOException e) {
            LOGGER.error("Could not get input stream for entry '" + entryName + "'");
            return null;
        }
    }

    /**
     * returns an accessible file with the given name relative to the model - basepath from the resources in the
     * classpath or zip file.
     * 
     * @param modelFile the {@link ModelFile} for which to get the rel file.
     * @return the file
     */
    private File getArchiveFile(ModelFile modelFile) {
        String filename = getArchivePath(modelFile);
        File file = getFileUtil().getOutputFile(filename);

        /*
         * For the moment all 'Archive types' (ZIP, JAR, and FILE) are treated the same. Maybe change this at some point
         * so that real files are not copied to the tmp path, but only extracted if necessary.
         */
        InputStream inputStream = getArchiveStream(modelFile);
        if (StreamUtil.toFile(inputStream, file)) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * get the stream for a real file. (This only returns a valid stream if the file is a repository file)
     * 
     * @param modelFile the model file for which to get the stream
     * @return the input stream from the file
     */
    private InputStream getRepositoryStream(ModelFile modelFile) {
        File file = getRepositoryFile(modelFile);
        if (file == null) {
            return null;
        } else {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                return null;
            }
        }
    }

    /**
     * returns a file object that represents the real file in the repository. This only returns a non null value for a
     * repository file
     * 
     * @param modelFile the model file for which
     * @return the file
     */
    private File getRepositoryFile(ModelFile modelFile) {
        String repoPath = getRepositoryPath(modelFile);
        return new File(repoPath);
    }

    @Override
    public String getArchivePath(ModelFile modelFile) {
        /* 1) the name itself */
        String resourcePath = modelFile.getName();
        /* 2) the model definition - dependent offset */
        resourcePath = prependPathOffset(resourcePath, modelFile.getLocation().getPathOffset(this.modelPathOffsets));
        /* 3) the offset depending on the type (Resource or repo file) */
        resourcePath = prependPathOffset(resourcePath, modelFile.getLocation().getResourcePrefix());

        return resourcePath;
    }

    @Override
    public String getRepositoryPath(ModelFile modelFile) {
        String repoPath = modelFile.getName();
        repoPath = prependPathOffset(repoPath, getRepositoryPathOffset());
        repoPath = getPreferences().getModelRepositoryBasePath() + File.separator + repoPath;
        return repoPath;
    }

    private String getRepositoryPathOffset() {
        return this.modelPathOffsets.getRepositoryOffset();
    }

    public void setPreferences(JMadPreferences preferences) {
        this.preferences = preferences;
    }

    private JMadPreferences getPreferences() {
        if (this.preferences == null) {
            LOGGER.warn("Preferences not set. Maybe config error.");
        }
        return preferences;
    }

    public void setFileUtil(TempFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    private TempFileUtil getFileUtil() {
        if (this.fileUtil == null) {
            LOGGER.warn("FileUtil not set. Maybe config error.");
        }
        return fileUtil;
    }

    @Override
    public RepositoryFilePriority getRepositoryFilePriority() {
        return this.repositoryFilePriority;
    }

    @Override
    public void setRepositoryFilePriority(RepositoryFilePriority priority) {
        this.repositoryFilePriority = priority;
    }

    /**
     * decides from where the file will be finally taken.
     * 
     * @param modelFile the modelFile for which to decide
     * @return the FileSource
     */
    private FileSource getFileSource(ModelFile modelFile) {
        if (ModelFileLocation.RESOURCE.equals(modelFile.getLocation())) {
            return FileSource.ARCHIVE;
        } else if (ModelFileLocation.REPOSITORY.equals(modelFile.getLocation())) {
            if (RepositoryFilePriority.ARCHIVE.equals(getRepositoryFilePriority())) {
                /*
                 * If the file can be retrieved from the archive then the source shall be ARCHIVE
                 */
                if (isAvailableInArchive(modelFile)) {
                    return FileSource.ARCHIVE;
                } else {
                    return FileSource.REPOSITORY;
                }
            } else if (RepositoryFilePriority.REPOSITORY.equals(getRepositoryFilePriority())) {
                if (isAvailableInRepository(modelFile)) {
                    return FileSource.REPOSITORY;
                } else {
                    return FileSource.ARCHIVE;
                }
            } else {
                LOGGER.warn("Unhandled RepositoryFilePriority '" + getRepositoryFilePriority()
                        + "'. Will try to get modelfile from archive.");
                return FileSource.ARCHIVE;
            }
        } else {
            LOGGER.warn("Unhandled ModelFileLocation '" + modelFile.getLocation()
                    + "'. Will try to get modelfile from archive.");
            return FileSource.ARCHIVE;
        }
    }

    /**
     * checks if the file is available in the archive
     * 
     * @param modelFile the model file to check
     * @return true if it exists, false if not
     */
    private boolean isAvailableInArchive(ModelFile modelFile) {
        InputStream archiveStream = getArchiveStream(modelFile);
        if (archiveStream != null) {
            try {
                archiveStream.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing input stream.", e);
            }
            return true;
        }
        return false;
    }

    /**
     * checks if the file is available in the repository
     * 
     * @param modelFile the file to check
     * @return true if it exists, false if not
     */
    private boolean isAvailableInRepository(ModelFile modelFile) {
        File file = getRepositoryFile(modelFile);
        if ((file != null) && (file.exists())) {
            return true;
        }
        return false;
    }

    public void setSourceInformation(SourceInformation sourceInformation) {
        this.sourceInformation = sourceInformation;
    }

    private SourceInformation getSourceInformation() {
        return sourceInformation;
    }

    /**
     * this enum indicates from where the repository-file is taken. It is the result of a combination of
     * {@link ModelFileFinder.RepositoryFilePriority} and the availability.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private static enum FileSource {
        ARCHIVE {
            @Override
            public File getFile(ModelFileFinderImpl fileFinder, ModelFile modelFile) {
                return fileFinder.getArchiveFile(modelFile);
            }

            @Override
            public InputStream getStream(ModelFileFinderImpl fileFinder, ModelFile modelFile) {
                return fileFinder.getArchiveStream(modelFile);
            }
        },
        REPOSITORY {
            @Override
            public File getFile(ModelFileFinderImpl fileFinder, ModelFile modelFile) {
                return fileFinder.getRepositoryFile(modelFile);
            }

            @Override
            public InputStream getStream(ModelFileFinderImpl fileFinder, ModelFile modelFile) {
                return fileFinder.getRepositoryStream(modelFile);
            }
        };

        public abstract File getFile(ModelFileFinderImpl fileFinder, ModelFile modelFile);

        public abstract InputStream getStream(ModelFileFinderImpl fileFinder, ModelFile modelFile);

    }
}
