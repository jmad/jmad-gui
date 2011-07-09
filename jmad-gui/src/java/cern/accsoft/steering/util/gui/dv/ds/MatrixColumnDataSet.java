package cern.accsoft.steering.util.gui.dv.ds;


public class MatrixColumnDataSet extends MatrixRowColDataSet {

	/**
	 * the constructor, which sets the initial values
	 * 
	 * @param name
	 *            the name of the dataset
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
	 * @param column
	 *            the column which to display
	 */
	public void setColumn(int column) {
		setFixedIndex(column);
	}
}
