/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.output;

/**
 * @author muellerg
 */
public class MatchConstraintResultGlobal extends AbstractMatchConstraintResult {

    public MatchConstraintResultGlobal(String constraint, double finalValue) {
        super(constraint, finalValue);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

}
