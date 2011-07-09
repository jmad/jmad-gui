/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.methods;

/**
 * The MIGRAD command minimizes the penalty function using the numerical derivatives of the sum of squares
 * 
 * @author muellerg
 */
public class MatchMethodMigrad extends AbstractMatchMethod {

    private static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.MIGRAD;

    @Override
    public AlgorithmType getAlgorithmType() {
        return ALGORITHM_TYPE;
    }
}
