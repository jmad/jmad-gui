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
package cern.accsoft.steering.jmad.domain.file;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * The default implementation of a modelFile. Immutable.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
@XStreamAlias("call-file")
public class CallableModelFileImpl extends AbstractModelFile implements CallableModelFile, Cloneable {

    /**
     * default constructor, Necessary for XStream
     */
    public CallableModelFileImpl() {
        this(null);
    }

    public CallableModelFileImpl(String path, ModelFileLocation location) {
        super(path, location);
    }

    public CallableModelFileImpl(String path) {
        super(path);
    }

    /** The parse type which is used when it is null after reading */
    static final ParseType DEFAULT_PARSE_TYPE = ParseType.NONE;

    /** per default the file will not be parsed when called */
    @XStreamAlias("parse")
    @XStreamAsAttribute
    private ParseType parseType = DEFAULT_PARSE_TYPE;

    /**
     * a constructor with the additional parse type
     * 
     * @param path the relative path of the file
     * @param location the location (repository or resource) where to search for the file
     * @param parseType defines how to interprete the file
     */
    public CallableModelFileImpl(String path, ModelFileLocation location, ParseType parseType) {
        super(path, location);
        this.parseType = parseType;
    }

    @Override
    public ParseType getParseType() {
        return parseType;
    }

    private Object writeReplace() {
        CallableModelFileImpl writtenObj;
        try {
            writtenObj = clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
        super.fillWriteReplace(writtenObj);
        if (DEFAULT_PARSE_TYPE == this.parseType) {
            writtenObj.parseType = null;
        }
        return writtenObj;
    }

    private Object readResolve() {
        super.abstractReadResolve();
        if (parseType == null) {
            parseType = DEFAULT_PARSE_TYPE;
        }
        return this;
    }
}
