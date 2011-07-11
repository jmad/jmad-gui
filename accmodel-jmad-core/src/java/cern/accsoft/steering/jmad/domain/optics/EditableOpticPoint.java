package cern.accsoft.steering.jmad.domain.optics;

import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public interface EditableOpticPoint extends OpticPoint {

    public abstract void setValue(MadxTwissVariable variable, Double value);

    public abstract void setBetx(Double betx);

    public abstract void setBety(Double bety);

    public abstract void setMux(Double mux);

    public abstract void setMuy(Double muy);

    /* short name to use the same as madx */
    public abstract void setDx(Double dx); // NOPMD by kaifox on 6/25/10 5:48 PM

    /* short name to use the same as madx */
    public abstract void setDy(Double dy); // NOPMD by kaifox on 6/25/10 5:49 PM

    public abstract void setAlfx(Double alfx);

    public abstract void setAlfy(Double alfy);

    public abstract void setDdx(Double ddx);

    public abstract void setDpx(Double dpx);

    public abstract void setDpy(Double dpy);

    public abstract void setDdy(Double ddy);

    public abstract void setDdpx(Double ddpx);

    public abstract void setDdpy(Double ddpy);

    /* short name to use the same as madx */
    public abstract void setX(Double x); // NOPMD by kaifox on 6/25/10 5:49 PM

    /* short name to use the same as madx */
    public abstract void setPx(Double px); // NOPMD by kaifox on 6/25/10 5:49 PM

    /* short name to use the same as madx */
    public abstract void setY(Double y); // NOPMD by kaifox on 6/25/10 5:50 PM

    /* short name to use the same as madx */
    public abstract void setPy(Double py); // NOPMD by kaifox on 6/25/10 5:50 PM

}