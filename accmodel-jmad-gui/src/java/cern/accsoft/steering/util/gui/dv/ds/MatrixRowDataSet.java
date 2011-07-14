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
package cern.accsoft.steering.util.gui.dv.ds;


public class MatrixRowDataSet extends MatrixRowColDataSet {

	/**
	 * the constructor, which sets the initial parameters
	 * 
	 * @param name
	 *            the name of the dataSet
	 */
	public MatrixRowDataSet(String name) {
		super(MatrixDsType.ROW, name);
	}

	public int getRow() {
		return getFixedIndex();
	}

	public void setRow(int row) {
		setFixedIndex(row);
	}

}
