package cern.accsoft.steering.jmad.domain.result.match.output;

public interface MatchConstraintResult {

    public abstract boolean isGlobal();

    public abstract String getConstraint();

    public abstract double getTargetValue();

    public abstract double getFinalValue();

    // TODO extract Type --> math Relation [>,<,< and >,=] == Type [1,2,3,4]
    // applicable as soon as type != 4 --> '=' is implemented in
    // MatchConstraint...
}
