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
