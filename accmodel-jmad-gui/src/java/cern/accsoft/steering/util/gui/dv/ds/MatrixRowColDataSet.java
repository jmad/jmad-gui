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

import java.util.List;

import Jama.Matrix;

/**
 * this class represents a dataset for the dataviewer, which either displays a row or a column of a matrix. The number
 * of the displayed column/row is determined by the fixedIndex property. The type is determined by the
 * 
 * @author kfuchsbe
 */
public abstract class MatrixRowColDataSet extends ValidityMatrixDataSet {

    /** the matrix which shall be displayed */
    private Matrix matrix = new Matrix(1, 1);

    /**
     * an optional matrix, which will be added or subtracted from the original matrix, depending on the offsetSubtract -
     * flag.
     */
    private Matrix offsetMatrix;

    /**
     * if set to true, the offset is subtracted from the matrix, otherwise it is added.
     */
    private boolean offsetSubtract = false;

    /** the type of this DataSet. */
    private final MatrixDsType type;

    /** the index of the displayed col/row (according to type) */
    private int fixedIndex = 0;

    /**
     * The first index of the row/col to display (inclusive). This might be <code>null</code>: in this case the first
     * index is the first row/column in of the matrix.
     */
    private Integer firstIndex = null;

    /**
     * The last index of the row/col to display (exclusive, dataCount will be lastIndex-firstIndex). This might be
     * <code>null</code>: In this case the last index is the last index of the matrix.
     */
    private Integer lastIndex = null;

    /**
     * the only constructor, which enforces setting of the initial paramters.
     * 
     * @param type the type of this dataset (row/column)
     * @param name the name of the dataset
     */
    public MatrixRowColDataSet(MatrixDsType type, String name) {
        super(name);
        this.type = type;
    }

    /**
     * This method determines the indizes for row and column in the matrix.
     * 
     * @param index the index for which to find the row and column
     * @return the row/col pair for the index
     */
    private RowCol getRowCol(int index) {
        int idx = index;
        if (this.firstIndex != null) {
            idx += this.firstIndex;
        }
        if (MatrixDsType.ROW.equals(this.type)) {
            return new RowCol(getFixedIndex(), idx);
        } else if (MatrixDsType.COLUMN.equals(this.type)) {
            return new RowCol(idx, getFixedIndex());
        }
        return null;
    }

    @Override
    public int getDataCount() {
        int first = 0;
        if (this.firstIndex != null) {
            first = this.firstIndex;
        }

        int last = 0;
        if (this.lastIndex == null) {
            if (matrix != null) {
                if (MatrixDsType.ROW.equals(this.type)) {
                    last = matrix.getColumnDimension();
                } else if (MatrixDsType.COLUMN.equals(this.type)) {
                    last = matrix.getRowDimension();
                }
            }
        } else {
            last = lastIndex;
        }

        return last - first;
    }

    /**
     * @return the type
     */
    public final MatrixDsType getType() {
        return type;
    }

    @Override
    public double getY(int index) {
        RowCol rowCol = getRowCol(index);
        double value = getMatrixValue(this.matrix, rowCol);
        double offset = getMatrixValue(this.offsetMatrix, rowCol);
        if (isOffsetSubtract()) {
            return (value - offset);
        } else {
            return (value + offset);
        }
    }

    /**
     * @param m the matrix for which to return the value
     * @param rowCol the rows/col for which to get the value
     * @return the value
     */
    private final static double getMatrixValue(Matrix m, RowCol rowCol) {
        if (rowCol != null) {
            if ((m != null) && (rowCol.row < m.getRowDimension()) && (rowCol.col < m.getColumnDimension())) {
                return m.get(rowCol.row, rowCol.col);
            }
        }
        return 0;
    }

    @Override
    public boolean getValidity(int index) {
        RowCol rowCol = getRowCol(index);
        if (rowCol != null) {
            return getValidity(rowCol.row, rowCol.col);
        }
        return false;
    }

    /**
     * @return the matrix, which is displayed
     */
    public Matrix getMatrix() {
        return matrix;
    }

    /**
     * set the matrix, which shall be displayed.
     * 
     * @param matrix the matrix to display
     */
    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
        fireFullChange();
    }

    /**
     * sets the matrix + additional xValues
     * 
     * @param matrix
     * @param xValues
     */
    public void setMatrix(Matrix matrix, List<Double> xValues) {
        super.setXValues(xValues);
        this.matrix = matrix;
        fireFullChange();
    }

    /**
     * @param fixedIndex the fixedIndex to set
     */
    public void setFixedIndex(int fixedIndex) {
        this.fixedIndex = fixedIndex;
        fireFullChange();
    }

    /**
     * @return the fixedIndex
     */
    public int getFixedIndex() {
        return fixedIndex;
    }

    /**
     * @param offsetMatrix the offsetMatrix to set
     */
    public void setOffsetMatrix(Matrix offsetMatrix) {
        this.offsetMatrix = offsetMatrix;
        fireFullChange();
    }

    /**
     * @return the offsetMatrix
     */
    public Matrix getOffsetMatrix() {
        return offsetMatrix;
    }

    /**
     * @param offsetSubtract the offsetSubtract to set
     */
    public void setOffsetSubtract(boolean offsetSubtract) {
        this.offsetSubtract = offsetSubtract;
        fireFullChange();
    }

    /**
     * @return the offsetSubtract
     */
    public boolean isOffsetSubtract() {
        return offsetSubtract;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
        fireFullChange();
    }

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
        fireFullChange();
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    /**
     * a simple row/col index - pair
     * 
     * @author kfuchsbe
     */
    private class RowCol {
        private final int row;
        private final int col;

        private RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * this enum represents the two possible different types of a {@link MatrixRowColDataSet}: either it displayes one
     * column, or one row.
     * 
     * @author kfuchsbe
     */
    public enum MatrixDsType {
        ROW, COLUMN;
    }
}