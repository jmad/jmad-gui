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
