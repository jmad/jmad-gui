/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.output;

/**
 * @author muellerg
 */
public class MadxVaryResultImpl implements MadxVaryResult {

    private final String name;
    private final double finalValue;

    public MadxVaryResultImpl(String name, double finalValue) {

        this.name = name;
        this.finalValue = finalValue;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getFinalValue() {
        return this.finalValue;
    }

    // @Override
    // public double getInitialValue() {
    // return this.initialValue;
    // }
    //
    // @Override
    // public double getLowerLimit() {
    // return this.lowerLimit;
    // }
    //
    // @Override
    // public double getUpperLimit() {
    // return this.upperLimit;
    // }

}
