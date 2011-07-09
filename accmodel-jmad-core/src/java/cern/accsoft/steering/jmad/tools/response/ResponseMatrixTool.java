/**
 * 
 */
package cern.accsoft.steering.jmad.tools.response;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.model.JMadModel;

/**
 * This is the interface of a simple tool, which can calculate a response matrix.
 * 
 * @author kfuchsbe
 */
public interface ResponseMatrixTool {

    /**
     * calculates the actual response-matrix. Which correctors, monitors to use and how to vary the strengthes is
     * defined via the ResponseRequest object.
     * 
     * @param model the model to use to calculate the response matrix
     * @param request contains strenghtes, monitors and strength-variations to use to calc the response-matrix.
     * @return the response-matrix
     * @throws JMadModelException if the calculation of the response matrix fails
     */
    public abstract Matrix calcResponseMatrix(JMadModel model, ResponseRequest request) throws JMadModelException;

}
