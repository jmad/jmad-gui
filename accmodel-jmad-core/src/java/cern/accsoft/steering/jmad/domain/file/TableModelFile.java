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
