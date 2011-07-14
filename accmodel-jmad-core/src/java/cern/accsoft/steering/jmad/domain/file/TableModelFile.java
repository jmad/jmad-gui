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
package cern.accsoft.steering.jmad.domain.file;

/**
 * a file for the model-definition that will be loaded as table.
 * 
 * @author kaifox
 */
public interface TableModelFile extends ModelFile {

    /**
     * return the name of the table which will hold the data after loadting.
     * <p>
     * If this is <code>null</code> then readtyble will be used. Otherwise 'readmytable' will be used in madx.
     * 
     * @return the name of the table where to load the file into
     */
    public String getTableName();

}
