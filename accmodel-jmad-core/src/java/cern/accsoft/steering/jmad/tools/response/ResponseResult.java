/**
 * 
 */
package cern.accsoft.steering.jmad.tools.response;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * The result of a response-matrix calculation
 * 
 * @author kfuchsbe
 */
public interface ResponseResult {

    /**
     * @return the full calculated Response matrix (H+V)
     */
    public Matrix getResponseMatrix();

    /**
     * the trajectory of a response calculation for one plane and one sign
     * 
     * @param plane the plane for which to get the trajectory
     * @param sign the sign for which to get the trajectory
     * @return a matrix which contains the trajectories for the given plane and sign
     */
    public Matrix getTrajectoryMatrix(JMadPlane plane, DeflectionSign sign);
}
