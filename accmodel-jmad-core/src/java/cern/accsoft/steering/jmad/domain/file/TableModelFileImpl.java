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
/**
 * 
 */
package cern.accsoft.steering.jmad.domain.file;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * A model file which will be loaded as table
 * 
 * @author kaifox
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
