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

package cern.accsoft.steering.util.gui.dv.ds;

public class MatrixColumnDataSet extends MatrixRowColDataSet {

    /**
     * the constructor, which sets the initial values
     * 
     * @param name the name of the dataset
     */
    public MatrixColumnDataSet(String name) {
        super(MatrixDsType.COLUMN, name);
    }

    /**
     * @return the actual displayed column
     */
    public int getColumn() {
        return getFixedIndex();
    }

    /**
     * @param column the column which to display
     */
    public void setColumn(int column) {
        setFixedIndex(column);
    }
}
