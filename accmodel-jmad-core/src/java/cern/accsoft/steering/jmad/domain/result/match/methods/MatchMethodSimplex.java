/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.methods;

/**
 * The SIMPLEX Method minimizes the penalty function by the simplex method
 * 
 * @author muellerg
 */
public class MatchMethodSimplex extends AbstractMatchMethod {

    private static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.SIMPLEX;

    @Override
    public AlgorithmType getAlgorithmType() {
        return ALGORITHM_TYPE;
    }
}
