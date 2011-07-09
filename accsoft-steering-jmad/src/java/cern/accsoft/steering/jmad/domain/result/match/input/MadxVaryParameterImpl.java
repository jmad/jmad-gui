/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.input;

import cern.accsoft.steering.jmad.domain.knob.MadxParameter;

/**
 * this
 * 
 * @author muellerg
 */
public class MadxVaryParameterImpl implements MadxVaryParameter {

    /** the madx knob to vary */
    private final MadxParameter parameter;

    /** the step for the variation */
    private Double step = null;

    public void setStep(Double step) {
        this.step = step;
    }

    public void setLower(Double lower) {
        this.lower = lower;
    }

    public void setUpper(Double upper) {
        this.upper = upper;
    }

    /** the lower limit */
    private Double lower = null;

    /** the upper limit */
    private Double upper = null;

    public MadxVaryParameterImpl(MadxParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public String getName() {
        return this.parameter.getMadxName();
    }

    @Override
    public Double getLower() {
        return this.lower;
    }

    @Override
    public Double getStep() {
        return this.step;
    }

    @Override
    public Double getUpper() {
        return this.upper;
    }

    @Override
    public MadxParameter getMadxParameter() {
        return this.parameter;
    }

}
