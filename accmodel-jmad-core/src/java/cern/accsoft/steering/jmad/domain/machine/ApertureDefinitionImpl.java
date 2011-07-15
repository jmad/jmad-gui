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
