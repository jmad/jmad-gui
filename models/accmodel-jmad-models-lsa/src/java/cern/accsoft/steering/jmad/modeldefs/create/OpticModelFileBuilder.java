/*
 * $Id $
 * 
 * $Date$ $Revision$ $Author$
 * 
 * Copyright CERN ${year}, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.modeldefs.create;

import cern.accsoft.steering.jmad.domain.file.CallableModelFileImpl;
import cern.accsoft.steering.jmad.domain.file.ModelFile;
import cern.accsoft.steering.jmad.domain.file.CallableModelFile.ParseType;
import cern.accsoft.steering.jmad.domain.file.ModelFile.ModelFileLocation;

public class OpticModelFileBuilder {

    private String path;
    private ParseType parseType = ParseType.STRENGTHS;
    private ModelFileLocation location = ModelFileLocation.REPOSITORY;

    protected OpticModelFileBuilder(String filePath) {
        this.path = filePath;
    }

    /**
     * Create a optic model file builder which would create a optic model file via {@link #build()} that is a repository
     * file and is parsed as a strength file.
     * 
     * @param filePath the relative path of the optic file with respect to the model settings
     * @return
     */
    public static OpticModelFileBuilder createInstance(String filePath) {
        return new OpticModelFileBuilder(filePath);
    }

    public OpticModelFileBuilder setParseType(ParseType parseType) {
        this.parseType = parseType;
        return this;
    }

    public OpticModelFileBuilder doNotParse() {
        return this.setParseType(ParseType.NONE);
    }

    public OpticModelFileBuilder setLocation(ModelFileLocation location) {
        this.location = location;
        return this;
    }

    public OpticModelFileBuilder isResource() {
        return this.setLocation(ModelFileLocation.RESOURCE);
    }

    public ModelFile build() {
        return new CallableModelFileImpl(this.path, this.location, this.parseType);
    }
}
