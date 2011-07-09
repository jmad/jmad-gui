package cern.accsoft.steering.jmad.domain.machine;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;

public class ApertureDefinitionImpl implements ApertureDefinition {

    /** the file that contains all the position of the slides etc. */
    private final ModelFile indexFile;

    /** all the part files for the aperture */
    private final List<ModelFile> partFiles = new ArrayList<ModelFile>();

    /**
     * the constructor which enforces to provide at least the index-file
     * 
     * @param indexFile the file with the aperture index
     */
    public ApertureDefinitionImpl(ModelFile indexFile) {
        this.indexFile = indexFile;
    }

    @Override
    public ModelFile getIndexFile() {
        return this.indexFile;
    }

    @Override
    public List<ModelFile> getPartFiles() {
        return this.partFiles;
    }

    /**
     * adds a file which contains part of the aperture values to this definition
     * 
     * @param partFile the file to add.
     */
    public void addPartFile(ModelFile partFile) {
        this.partFiles.add(partFile);
    }

}
