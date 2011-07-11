package cern.accsoft.steering.jmad.domain.optics;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;
import cern.accsoft.steering.jmad.domain.var.enums.JMadTwissVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public interface OpticPoint {

    public abstract Double getValue(JMadTwissVariable variable, JMadPlane plane);

    public abstract Double getValue(MadxTwissVariable variable);

    public abstract Double getBetx();

    public abstract Double getBety();

    public abstract Double getMux();

    public abstract Double getMuy();

    public abstract String getName();
    
    public abstract Double getPosition();

    public abstract Double getDx();

    public abstract Double getDy();

    public abstract Double getAlfx();

    public abstract Double getAlfy();

    public abstract Double getDdx();

    public abstract Double getDdy();

    public abstract Double getDdpx();

    public abstract Double getDdpy();

    public abstract Double getDpx();

    public abstract Double getDpy();

    public abstract Double getX();

    public abstract Double getPx();

    public abstract Double getY();

    public abstract Double getPy();

}