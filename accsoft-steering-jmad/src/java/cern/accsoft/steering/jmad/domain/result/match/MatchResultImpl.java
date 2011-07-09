package cern.accsoft.steering.jmad.domain.result.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.result.match.output.MadxVaryResult;
import cern.accsoft.steering.jmad.domain.result.match.output.MatchConstraintResult;

public class MatchResultImpl implements MatchResult {

    private boolean successful = false;

    private final double finalPenalty;
    private final List<MatchConstraintResult> conResults;
    private final List<MadxVaryResult> varyResults;

    public MatchResultImpl(double finalPenalty) {
        this.finalPenalty = finalPenalty;
        this.conResults = new ArrayList<MatchConstraintResult>();
        this.varyResults = new ArrayList<MadxVaryResult>();
    }

    public void addConstrainParameterResult(MatchConstraintResult result) {
        this.conResults.add(result);
    }

    @Override
    public List<MatchConstraintResult> getConstraintParameterResults() {
        return this.conResults;
    }

    public void addVaryParameterResult(MadxVaryResult result) {
        this.varyResults.add(result);
    }

    @Override
    public List<MadxVaryResult> getVaryParameterResults() {
        return this.varyResults;
    }

    @Override
    public boolean isSuccessful() {
        return this.successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.MATCH_RESULT;
    }

    @Override
    public double getFinalPenalty() {
        return this.finalPenalty;
    }
}
