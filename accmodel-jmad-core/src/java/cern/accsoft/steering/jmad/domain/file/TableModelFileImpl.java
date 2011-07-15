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
 * A model file which will be loaded as table
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
@XStreamAlias("table-file")
public class TableModelFileImpl extends AbstractModelFile implements TableModelFile {

    /** The name of the table to load the file into */
    @XStreamAlias("table-name")
    @XStreamAsAttribute
    private String tableName = null;

    /**
     * default constructor. Necessary for XStream
     */
    public TableModelFileImpl() {
        this(null, null);
    }

    public TableModelFileImpl(String path, ModelFileLocation location) {
        super(path, location);
    }

    public TableModelFileImpl(String path, ModelFileLocation location, String tableName) {
        this(path, location);
        this.tableName = tableName;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

}
