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

package cern.accsoft.steering.jmad.domain.result.tfs;

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.var.MadxVariable;
import cern.accsoft.steering.jmad.util.MadxVarType;

public interface TfsResult extends Result {

    /**
     * @return the amount of data-columns (amount of variables)
     */
    public abstract int getColumnCount();

    /**
     * @return the summary parameters
     */
    public abstract TfsSummary getSummary();

    /**
     * returns one Column of Data as double-values.
     * 
     * @param resultVariable the ResultVariable for which to get the Data.
     * @return the Values
     */
    public abstract List<Double> getDoubleData(MadxVariable resultVariable);

    /**
     * returns one Column of Data ad double-values
     * 
     * @param key the key for the data
     * @return the values
     */
    public abstract List<Double> getDoubleData(String key);

    /**
     * returns one Column of Data as String-values.
     * 
     * @param resultVariable the ResultVariable for which to get the Data.
     * @return the Values
     */
    public abstract List<String> getStringData(MadxVariable resultVariable);

    /**
     * returns the Data-Column for the given Key as ArrayList<Double>
     * 
     * @param key the key for which to get the data.
     * @return the Double values
     */
    public abstract List<String> getStringData(String key);

    /**
     * @return the available keys.
     */
    public abstract List<String> getKeys();

    /**
     * @param elementName the name of the element for which one wants to retrieve the index.
     * @return the index of Element in a list of values.
     */
    public Integer getElementIndex(String elementName);

    /**
     * returns the type of Data for the given key
     * 
     * @param key the key for which to get the DataType
     * @return the Data-Type
     */
    public MadxVarType getVarType(String key);

    /**
     * returns the type of Data for a given ResultVariable
     * 
     * @param var the ResultVariable
     * @return the VarType
     */
    public MadxVarType getVarType(MadxVariable var);

}
