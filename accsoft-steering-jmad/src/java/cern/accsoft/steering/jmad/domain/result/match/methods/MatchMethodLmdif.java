/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.methods;

/**
 * The LMDIF command minimises the sum of squares of the constraint functions using their numerical derivatives:
 * LMDIF,CALLS=integer,TOLERANCE=real; It is the fastest minimisation method available in MAD. The command has two
 * attributes: CALLS: The maximum number of calls to the penalty function (default: 1000). TOLERANCE: The desired
 * tolerance for the minimum (default: 10**(-6)). Example: LMDIF,CALLS=2000,TOLERANCE=1.0E-8;
 * 
 * @author muellerg
 */
public class MatchMethodLmdif extends AbstractMatchMethod {

    private static final AlgorithmType ALGORITHM_TYPE = AlgorithmType.LMDIF;

    @Override
    public AlgorithmType getAlgorithmType() {
        return ALGORITHM_TYPE;
    }
}
