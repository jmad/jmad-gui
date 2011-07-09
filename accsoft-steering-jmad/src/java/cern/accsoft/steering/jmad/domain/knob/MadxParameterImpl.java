/**
 * 
 */
package cern.accsoft.steering.jmad.domain.knob;

/**
 * @author muellerg
 */
public class MadxParameterImpl implements MadxParameter {

    private final String name;

    public MadxParameterImpl(String name) {
        this.name = name;
    }

    @Override
    public String getMadxName() {
        return this.name;
    }

}
