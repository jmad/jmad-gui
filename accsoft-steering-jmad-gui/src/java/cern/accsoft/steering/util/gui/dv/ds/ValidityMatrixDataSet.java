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