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

import cern.accsoft.steering.util.TMatrix;

public abstract class ValidityMatrixDataSet extends AbstractJmadDataSet implements ValidityDataSet {

    private TMatrix<Boolean> validityMatrix;

    public ValidityMatrixDataSet(String name) {
        super(name);
    }

    /**
     * @param validityMatrix the validityMatrix to set
     */
    public final void setValidityMatrix(TMatrix<Boolean> validityMatrix) {
        this.validityMatrix = validityMatrix;
    }

    /**
     * returns the validity for a given element
     * 
     * @param row
     * @param col
     * @return
     */
    protected boolean getValidity(int row, int col) {
        if ((validityMatrix != null) && (col < validityMatrix.getColumnDimension())
                && (row < validityMatrix.getRowDimension())) {
            return validityMatrix.get(row, col);
        }
        return false;
    }

    @Override
    public boolean hasValidityInformation() {
        return (this.validityMatrix != null);
    }

}