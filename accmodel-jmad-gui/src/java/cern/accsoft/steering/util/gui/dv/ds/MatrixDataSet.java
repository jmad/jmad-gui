package cern.accsoft.steering.util.gui.dv.ds;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import cern.jdve.data.AbstractDataSet3D;
import cern.jdve.event.DataSetEvent;
import cern.jdve.utils.DataRange;

/**
 * implements the interface <code>DataSet3D</code> in order to map Matrix-values
 * to Z-coordinates.
 * 
 * @author kfuchsbe
 * 
 */
public class MatrixDataSet extends AbstractDataSet3D {
	private Matrix matrix = null;

	private List<String> rowLabels = new ArrayList<String>();
	private List<String> columnLabels = new ArrayList<String>();

	/* transpose the matrix */
	boolean transposed = false;

	public MatrixDataSet(String name, Matrix matrix) {
		super(name);
		this.matrix = matrix;
	}

	public MatrixDataSet(String name, Matrix matrix, boolean transposed) {
		super(name);
		this.matrix = matrix;
		this.transposed = transposed;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
		fireDataSetChanged(new DataSetEvent(this, DataSetEvent.FULL_CHANGE));
	}

	public void setTransposed(boolean transposed) {
		this.transposed = transposed;
		fireDataSetChanged(new DataSetEvent(this, DataSetEvent.FULL_CHANGE));
	}

	public boolean isTransposed() {
		return transposed;
	}

	public String getDataLabel(int index) {
		return "Index " + index;
	}

	@Override
	public int getXDataCount() {
		if (matrix == null) {
			return 1;
		}

		if (transposed) {
			return matrix.getRowDimension();
		} else {
			return matrix.getColumnDimension();
		}
	}

	@Override
	public int getYDataCount() {
		if (matrix == null) {
			return 1;
		}

		if (transposed) {
			return matrix.getColumnDimension();
		} else {
			return matrix.getRowDimension();
		}
	}

	private boolean isLimitsOk(int xIndex, int yIndex) {
		if ((xIndex < 0) || (yIndex < 0)) {
			return false;
		}
		if (transposed) {
			return ((xIndex < matrix.getRowDimension()) && (yIndex < matrix
					.getColumnDimension()));
		} else {
			return ((xIndex < matrix.getColumnDimension()) && (yIndex < matrix
					.getRowDimension()));
		}
	}

	@Override
	public double getZ(int xIndex, int yIndex) {
		if ((matrix == null) || !isLimitsOk(xIndex, yIndex)) {
			return 0;
		}

		if (transposed) {
			return matrix.get(xIndex, yIndex);
		} else {
			return matrix.get(yIndex, xIndex);
		}
	}

	@Override
	public DataRange getZRange() {
		return calcZRange();
	}

	@Override
	public double getX(int index) {
		return index;
	}

	@Override
	public DataRange getXRange() {
		if (matrix == null) {
			return new DataRange(0, 0);
		}

		if (transposed) {
			return new DataRange(0, matrix.getRowDimension() - 1);
		} else {
			return new DataRange(0, matrix.getColumnDimension() - 1);
		}
	}

	@Override
	public double getY(int index) {
		return index;
	}

	@Override
	public DataRange getYRange() {
		if (matrix == null) {
			return new DataRange(0, 0);
		}

		if (transposed) {
			return new DataRange(0, matrix.getColumnDimension() - 1);
		} else {
			return new DataRange(0, matrix.getRowDimension() - 1);
		}
	}

	@Override
	public int indexOf(double coordinate) {
		return new Double(coordinate).intValue();
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	private DataRange calcZRange() {
		if (matrix == null) {
			return new DataRange(0, 0);
		}

		double minZ = 0;
		double maxZ = 0;

		for (int i = 0; i < matrix.getRowDimension(); i++) {
			for (int j = 0; j < matrix.getColumnDimension(); j++) {
				double value = matrix.get(i, j);
				if ((i == 0) && (j == 0)) {
					minZ = value;
					maxZ = value;
				} else {
					minZ = Math.min(minZ, value);
					maxZ = Math.max(maxZ, value);
				}
			}
		}

		return new DataRange(minZ, maxZ);
	}

	public void setRowLabels(List<String> labels) {
		this.rowLabels = labels;
	}

	public void setColumnLabels(List<String> labels) {
		this.columnLabels = labels;
	}

	public String getXLabel(int xIndex) {
		List<String> labels = null;
		if (transposed) {
			labels = rowLabels;
		} else {
			labels = columnLabels;
		}
		if (xIndex < labels.size()) {
			return labels.get(xIndex);
		}
		return null;
	}

	public String getYLabel(int yIndex) {
		List<String> labels = null;
		if (transposed) {
			labels = columnLabels;
		} else {
			labels = rowLabels;
		}
		if (yIndex < labels.size()) {
			return labels.get(yIndex);
		}
		return null;
	}

	public int getColumnNumber(int xIndex, int yIndex) {
		if (getXRange().contains(xIndex) && getYRange().contains(yIndex)) {
			if (transposed) {
				return yIndex;
			} else {
				return xIndex;
			}
		} else {
			return 0;
		}

	}

	public int getRowNumber(int xIndex, int yIndex) {
		if (getXRange().contains(xIndex) && getYRange().contains(yIndex)) {
			if (transposed) {
				return xIndex;
			} else {
				return yIndex;
			}
		} else {
			return 0;
		}
	}

	public String getRowLabel(int row) {
		if ((row >= 0) && (row < rowLabels.size())) {
			return rowLabels.get(row);
		}
		return null;
	}

	public String getColumnLabel(int column) {
		if ((column >= 0) && (column < columnLabels.size())) {
			return columnLabels.get(column);
		}
		return null;
	}

	@Override
	public void set(int index, int index2, double x, double y, double z) {
		throw new UnsupportedOperationException("not implemented");
	}
}
