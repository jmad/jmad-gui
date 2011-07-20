/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.create;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.file.ModelFile;

public class OpticDefinitionSetImpl implements OpticDefinitionSet {

    private List<ModelFile> finalCommonFiles = new ArrayList<ModelFile>();
    private List<ModelFile> initialCommonFiles = new ArrayList<ModelFile>();

    private List<String> opticNameList = new ArrayList<String>();
    private Map<String, List<ModelFile>> opticNameFileMapping = new HashMap<String, List<ModelFile>>();

    @Override
    public List<ModelFile> getFinalCommonOpticFiles() {
        return this.finalCommonFiles;
    }

    @Override
    public List<ModelFile> getInitialCommonOpticFiles() {
        return this.initialCommonFiles;
    }

    public void addInitialCommonFile(ModelFile modelFile) {
        this.initialCommonFiles.add(modelFile);
    }

    public void addFinalCommonFile(ModelFile modelFile) {
        this.finalCommonFiles.add(modelFile);
    }

    public void addOptic(String name, List<ModelFile> opticFiles) {
        this.opticNameList.add(name);
        this.opticNameFileMapping.put(name, opticFiles);
    }

    @Override
    public List<String> getDefinedOpticNames() {
        return Collections.unmodifiableList(this.opticNameList);
    }

    @Override
    public List<ModelFile> getOpticModelFiles(String opticName) {
        if (!this.opticNameFileMapping.containsKey(opticName)) {
            throw new IllegalArgumentException("Optic definition set does not contain model files for optic ["
                    + opticName + "]");
        }

        return this.opticNameFileMapping.get(opticName);
    }
}
