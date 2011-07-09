/**
 * 
 */
package cern.accsoft.steering.jmad.tools.response;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * The implementation of a ResponseResult
 * 
 * @author kfuchsbe
 */
public class ResponseResultImpl implements ResponseResult {

    private final Matrix responseMatrix;

    public ResponseResultImpl(int monitorCount, int correctorCount) {
        this.responseMatrix = new Matrix(correctorCount, monitorCount);
    }

    @Override
    public Matrix getResponseMatrix() {
        return this.responseMatrix;
    }

    @Override
    public Matrix getTrajectoryMatrix(JMadPlane plane, DeflectionSign sign) {
        // TODO Auto-generated method stub
        return null;
    }

}
