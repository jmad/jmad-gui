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
package cern.accsoft.steering.util.gui.dv.ds;

import cern.accsoft.steering.util.TMatrix;


public abstract class ValidityMatrixDataSet extends AbstractJmadDataSet implements
		ValidityDataSet {

	private TMatrix<Boolean> validityMatrix;

	public ValidityMatrixDataSet(String name) {
		super(name);
	}

	/**
	 * @param validityMatrix
	 *            the validityMatrix to set
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
		if ((validityMatrix != null)
				&& (col < validityMatrix.getColumnDimension())
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