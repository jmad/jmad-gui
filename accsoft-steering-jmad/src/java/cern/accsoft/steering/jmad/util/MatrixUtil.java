package cern.accsoft.steering.jmad.util;

import java.util.List;

import Jama.Matrix;

public final class MatrixUtil {

    private MatrixUtil() {
        /* only static methods */
    }

    /**
     * converts a list of doubles to a jama - matrix with one column.
     * 
     * @param values the list of values
     * @return a matrix containing the values.
     */
    public static Matrix createVector(List<Double> values) {
        Matrix vector = new Matrix(values.size(), 1);
        for (int i = 0; i < values.size(); i++) {
            vector.set(i, 0, values.get(i));
        }
        return vector;
    }

    /**
     * creates a new matrix that contains the square-roots of the elements of the input matrix.
     * 
     * @param matrix the matrix of whose elements to take the input values
     * @return a matrix containing the square-roots of the elements of the input matrix.
     */
    public static Matrix sqrtByElements(Matrix matrix) {
        Matrix outMatrix = new Matrix(matrix.getRowDimension(), matrix.getColumnDimension());

        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                outMatrix.set(i, j, Math.sqrt(matrix.get(i, j)));
            }
        }
        return outMatrix;
    }
}
