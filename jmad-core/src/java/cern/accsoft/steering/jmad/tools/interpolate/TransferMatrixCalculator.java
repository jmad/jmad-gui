package cern.accsoft.steering.jmad.tools.interpolate;

import Jama.Matrix;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;

public class TransferMatrixCalculator {

    /**
     * Calculate the transfer matrix from one optic point to another
     * 
     * @param plane The plane for which to calc the transfer matrix
     * @param from the starting point
     * @param to the ending point
     * @return the jama matrix containing the transfer matrix from {@link OpticPoint} FROM to {@link OpticPoint} TO
     */
    public static final Matrix calculate(JMadPlane plane, OpticPoint from, OpticPoint to) {
        Double beta1 = from.getValue(JMadTwissVariable.BETA, plane);
        Double beta2 = to.getValue(JMadTwissVariable.BETA, plane);

        Double alfa1 = from.getValue(JMadTwissVariable.ALFA, plane);
        Double alfa2 = to.getValue(JMadTwissVariable.ALFA, plane);

        Double mu1 = from.getValue(JMadTwissVariable.MU, plane);
        Double mu2 = to.getValue(JMadTwissVariable.MU, plane);

        double psi = 2 * Math.PI * (mu2 - mu1);
        double m11 = Math.sqrt(beta2 / beta1) * (Math.cos(psi) + alfa1 * Math.sin(psi));
        double m12 = Math.sqrt(beta1 * beta2) * Math.sin(psi);
        double m21 = ((alfa1 - alfa2) / (Math.sqrt(beta1 * beta2))) * Math.cos(psi)
                - ((1 + alfa1 * alfa2) / (Math.sqrt(beta1 * beta2))) * Math.sin(psi);
        double m22 = Math.sqrt(beta1 / beta2) * (Math.cos(psi) - alfa2 * Math.sin(psi));

        return new Matrix(new double[][] { new double[] { m11, m12 }, new double[] { m21, m22 } });
    }
}
