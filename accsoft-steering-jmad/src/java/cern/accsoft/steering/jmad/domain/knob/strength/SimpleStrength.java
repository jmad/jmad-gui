package cern.accsoft.steering.jmad.domain.knob.strength;

import cern.accsoft.steering.jmad.domain.knob.AbstractKnob;
import cern.accsoft.steering.jmad.domain.knob.KnobType;

public class SimpleStrength extends AbstractKnob implements Strength {

    private final String name;
    private double totalValue = 0.0;
    private String description = null;

    public SimpleStrength(String name, double value, String comment) {
        this.name = name;
        this.totalValue = value;
        this.description = comment;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected void doSetTotalValue(double value) {
        this.totalValue = value;
    }

    @Override
    public double getTotalValue() {
        return this.totalValue;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getKey() {
        return getName().trim().toLowerCase();
    }

    @Override
    public KnobType getType() {
        return KnobType.STRENGTH;
    }

    @Override
    public String getMadxName() {
        return getName();
    }

}
