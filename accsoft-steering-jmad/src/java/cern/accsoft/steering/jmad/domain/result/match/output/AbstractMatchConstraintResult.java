/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.output;

/**
 * @author muellerg
 */
public abstract class AbstractMatchConstraintResult implements MatchConstraintResult {

    private final String constraint;
    private double targetValue = Double.NaN;
    private final double finalValue;

    public AbstractMatchConstraintResult(String constraint, double finalValue) {
        this.constraint = constraint;
        this.finalValue = finalValue;
    }

    @Override
    public String getConstraint() {
        return this.constraint;
    }

    @Override
    public double getFinalValue() {
        return this.finalValue;
    }

    @Override
    public double getTargetValue() {
        return this.targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }
}
