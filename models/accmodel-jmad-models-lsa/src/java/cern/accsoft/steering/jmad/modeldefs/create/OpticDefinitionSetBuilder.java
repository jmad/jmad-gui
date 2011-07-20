/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.create;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.file.ModelFile;

public class OpticDefinitionSetBuilder {

    private OpticDefinitionSetImpl opticDefinitionSet;

    protected OpticDefinitionSetBuilder() {
        this.opticDefinitionSet = new OpticDefinitionSetImpl();
    }

    public static OpticDefinitionSetBuilder newInstance() {
        return new OpticDefinitionSetBuilder();
    }

    public OpticDefinitionSetBuilder addInitialCommonOpticFile(OpticModelFileBuilder opticModelFileBuilder) {
        this.opticDefinitionSet.addInitialCommonFile(opticModelFileBuilder.build());
        return this;
    }

    public OpticDefinitionSetBuilder addFinalCommonOpticFile(OpticModelFileBuilder opticModelFileBuilder) {
        this.opticDefinitionSet.addFinalCommonFile(opticModelFileBuilder.build());
        return this;
    }

    public OpticDefinitionSetBuilder addOptic(String opticName, OpticModelFileBuilder... opticModelFileBuilders) {
        List<ModelFile> opticFiles = new ArrayList<ModelFile>();
        for (OpticModelFileBuilder builder : opticModelFileBuilders) {
            opticFiles.add(builder.build());
        }

        this.opticDefinitionSet.addOptic(opticName, opticFiles);
        return this;
    }

    public OpticDefinitionSet build() {
        return this.opticDefinitionSet;
    }

}
