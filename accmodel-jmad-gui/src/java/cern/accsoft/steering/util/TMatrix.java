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
