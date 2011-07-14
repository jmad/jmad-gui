// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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
/**
 * 
 */
package cern.accsoft.steering.jmad.modeldefs.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.modeldefs.io.ModelFileFinder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * The default implementation of the Optics definition. to operate correctly it needs a {@link ModelFileFinder} and some
 * filenames.
 * 
 * @author kfuchsbe
 */
@XStreamAlias("optic")
public class OpticsDefinitionImpl implements OpticsDefinition, Cloneable {

    /** The files */
    @XStreamAlias("init-files")
    private List<ModelFile> initFiles = new ArrayList<ModelFile>();

    @XStreamAlias("post-ptc-files")
    private List<ModelFile> postPtcUniverseFiles = new ArrayList<ModelFile>();

    /** The name of this optics */
    @XStreamAlias("name")
    @XStreamAsAttribute
    private String name = null;

    /** per default we only have full optics */
    @XStreamAlias("overlay")
    @XStreamAsAttribute
    private boolean overlay = false;

    /**
     * no-args constructor for XStream
     */
    public OpticsDefinitionImpl() {
        super();
    }
    
    public OpticsDefinitionImpl(String name, ModelFile... modelFiles) {
        this.name = name;
        for (ModelFile modelFile : modelFiles) {
            this.initFiles.add(modelFile);
        }
    }

    public OpticsDefinitionImpl(String name, boolean overlay, ModelFile... modelFiles) {
        this(name, modelFiles);
        this.overlay = overlay;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public final String[] getOpticFileNames() {
        String[] paths = new String[this.initFiles.size()];
        int fileNumber = 0;
        for (ModelFile mFile : this.initFiles) {
            paths[fileNumber++] = mFile.getName();
        }

        return paths;
    }

    @Override
    public final Collection<ModelFile> getRequiredFiles() {
        Set<ModelFile> files = new HashSet<ModelFile>(getInitFiles());
        files.addAll(getPostPtcUniverseFiles());
        return files;
    }

    @Override
    public List<ModelFile> getInitFiles() {
        return this.initFiles;
    }

    @Override
    public boolean isOverlay() {
        return this.overlay;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public List<ModelFile> getPostPtcUniverseFiles() {
        return this.postPtcUniverseFiles;
    }

    public void addPostPtcUniverseFile(ModelFile modelFile) {
        this.postPtcUniverseFiles.add(modelFile);
    }

    private Object writeReplace() {
        OpticsDefinitionImpl writtenObj;
        try {
            writtenObj = clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }

        if (writtenObj.getInitFiles().isEmpty()) {
            writtenObj.initFiles = null;
        }
        if (writtenObj.getPostPtcUniverseFiles().isEmpty()) {
            writtenObj.postPtcUniverseFiles = null;
        }

        return writtenObj;
    }

    /**
     * is called after creating this object from xml. Some additional initialization is done here, since no emplty lists
     * are stored to xml.
     * 
     * @return the this object, fully configured.
     */
    private Object readResolve() {

        if (this.initFiles == null) {
            this.initFiles = new ArrayList<ModelFile>();
        }
        if (this.postPtcUniverseFiles == null) {
            this.postPtcUniverseFiles = new ArrayList<ModelFile>();
        }
        return this;
    }

    public OpticsDefinitionImpl clone() throws CloneNotSupportedException {
        OpticsDefinitionImpl object = (OpticsDefinitionImpl) super.clone();
        object.overlay = this.overlay;
        object.name = this.name;
        object.initFiles = new ArrayList<ModelFile>(this.initFiles);
        object.postPtcUniverseFiles = new ArrayList<ModelFile>(this.postPtcUniverseFiles);
        return object;
    }

}
