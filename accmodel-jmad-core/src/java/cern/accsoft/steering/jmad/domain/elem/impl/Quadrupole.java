package cern.accsoft.steering.jmad.domain.elem.impl;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;

/**
 * Represents in JMad an element of type <a
 * href="http://mad.web.cern.ch/mad/Introduction/quadrupole.html">QUADRUPOLE</a> in MadX.
 * 
 * @author kaifox
 */
public class Quadrupole extends AbstractElement {

    /** the normal quadrupole coefficent. */
    public static final String ATTR_K1 = "k1";

    /** the skew quadrupole coefficient. */
    public static final String ATTR_K1S = "k1s";

    /** the tilt of the quadrupole. */
    public static final String ATTR_TILT = "tilt";

    public Quadrupole(MadxElementType madxElementType, String name) {
        super(madxElementType, name);

        addAttribute(ATTR_K1);
        addAttribute(ATTR_K1S);
        addAttribute(ATTR_TILT);
    }

    public double getK1() {
        return getAttribute(ATTR_K1);
    }

    /* short variable name to use the same as madx */
    public void setK1(double k1) { // NOPMD by kaifox on 6/25/10 5:35 PM
        setAttribute(ATTR_K1, k1);
    }

    public double getK1s() {
        return getAttribute(ATTR_K1S);
    }

    public void setK1s(double k1s) {
        setAttribute(ATTR_K1S, k1s);
    }

    public void setTilt(double tilt) {
        setAttribute(ATTR_TILT, tilt);
    }

    public double getTilt() {
        return getAttribute(ATTR_TILT);
    }

}
