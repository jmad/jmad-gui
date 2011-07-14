// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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
