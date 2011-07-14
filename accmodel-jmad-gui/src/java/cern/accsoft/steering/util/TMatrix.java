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
package cern.accsoft.steering.util;

import java.util.ArrayList;

public class TMatrix<T> {

	private ArrayList<ArrayList<T>> matrix;
	private int rows;
	private int columns;
	
	public TMatrix(int rows, int columns, T initValue) {
		this.rows = rows;
		this.columns = columns;
		matrix = new ArrayList<ArrayList<T>>();
		for (int i = 0; i < rows; i++) {
			ArrayList<T> rowContent = new ArrayList<T>();
			for (int j = 0; j < columns; j++) {
				rowContent.add(initValue);
			}
			matrix.add(rowContent);
		}
	}
	
	public void set(int row, int col, T value) {
		matrix.get(row).set(col, value);
	}
	
	public T get(int row, int col) {
		return matrix.get(row).get(col);
	}
	
	public int getRowDimension() {
		return rows;
	}
	
	public int getColumnDimension() {
		return columns;
	}
}
