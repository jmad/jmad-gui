package cern.accsoft.steering.jmad.domain.result;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.strength.Strength;

public class StrengthResult implements Result {

    private final List<Strength> strengthes;
    private final List<Double> doubleValues = new ArrayList<Double>();

    public StrengthResult(List<Strength> strengthes) {
        this.strengthes = strengthes;
        for (Knob value : strengthes) {
            doubleValues.add(value.getValue());
        }
    }

    @Override
    public ResultType getResultType() {
        return ResultType.VALUES_RESULT;
    }

    public List<Strength> getValues() {
        return strengthes;
    }

    public List<Double> getDoubleValues() {
        return doubleValues;
    }

}
