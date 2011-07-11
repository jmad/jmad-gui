/**
 * 
 */
package cern.accsoft.steering.jmad.model;

import cern.accsoft.steering.jmad.domain.knob.KnobType;
import cern.accsoft.steering.jmad.domain.knob.bean.BeanPropertyKnob;

/**
 * this is a knob for a twiss initial condition.
 * 
 * @author kfuchsbe
 */
public class TwissInitialConditionKnob extends BeanPropertyKnob {

    private final JMadModel model;

    public TwissInitialConditionKnob(JMadModel model, String propertyName) {
        super(propertyName);
        this.model = model;
    }

    @Override
    public KnobType getType() {
        return getKnobType();
    }

    public static final KnobType getKnobType() {
        return KnobType.TWISS_INITIAL_CONDITION;
    }

    @Override
    protected Object getBean() {
        return this.model.getTwissInitialConditions();
    }

    @Override
    public String getDescription() {
        return null;
    }
}
