/*
 * $Id: Bend.java,v 1.1 2008-12-19 13:55:30 kfuchsbe Exp $
 * 
 * $Date: 2008-12-19 13:55:30 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.elem.impl;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;

/**
 * Represents a bending-magnet of a general type. This is the substitute in JMad for both MadX elements, <a
 * href="http://mad.web.cern.ch/mad/Introduction/bend.html">RBEND and SBEND</a>.
 * <p>
 * possible attributes:
 * <p>
 * L=real,ANGLE=real,TILT=real,K0=real,K0S=real,K1=real,E1=real,E2=real,
 * FINT=real,FINTX=real,HGAP=real,K2=real,H1=real,H2=real,K3=real;
 * 
 * @author kfuchsbe
 */
public class Bend extends AbstractElement {

    /** the bending angle. */
    public static final String ATTR_ANGLE = "angle";

    /** the tilt of the element */
    public static final String ATTR_TILT = "tilt";

    /** the dipole coeff. Should only be used to define relative field errors. */
    public static final String ATTR_K0 = "k0";

    /**
     * the skewed dipole coeff. Should only be used to define relative field errors.
     */
    public static final String ATTR_K0S = "k0s";

    /** the quadrupole coeff. */
    public static final String ATTR_K1 = "k1";

    /** the rotation angle for the entrance pole face (default: 0 rad). */
    public static final String ATTR_E1 = "e1";

    /** the rotation angle for the exit pole face (default: 0 rad). */
    public static final String ATTR_E2 = "e2";

    /** The field integral whose default value is 0. */
    public static final String ATTR_FINT = "fint";

    /**
     * Allows (FINTX > 0)to set FINT at the element exit different from its entry value. In particular useful to switch
     * it off (FINTX=0).
     */
    public static final String ATTR_FINTX = "fintx";

    /** the half gap of the magnet (default: 0 m). */
    public static final String ATTR_HGAP = "hgap";

    /** the sextupole coefficient K2 = (1 / B rho) (del2 By / del x2). */
    public static final String ATTR_K2 = "k2";

    /** the curvature of the entrance pole face (default: 0 m-1). */
    public static final String ATTR_H1 = "h1";

    /**
     * the curvature of the exit pole face (default: 0 m-1). A positive pole face curvature induces a negative sextupole
     * component; i.e. for positive H1 and H2 the centres of curvature of the pole faces are placed inside the magnet.
     */
    public static final String ATTR_H2 = "h2";

    /** the octupole coefficient */
    public static final String ATTR_K3 = "k3";

    /**
     * the constructor, which enforces that the element has a MadX element type and a name.
     * 
     * @param madxElementType the elementType which represents the element in MadX
     * @param name the name of the element
     */
    public Bend(MadxElementType madxElementType, String name) {
        super(madxElementType, name);
        addAttribute(ATTR_ANGLE);
        addAttribute(ATTR_E1);
        addAttribute(ATTR_E2);
        addAttribute(ATTR_FINT);
        addAttribute(ATTR_FINTX);
        addAttribute(ATTR_H1);
        addAttribute(ATTR_H2);
        addAttribute(ATTR_HGAP);
        addAttribute(ATTR_K0);
        addAttribute(ATTR_K0S);
        addAttribute(ATTR_K1);
        addAttribute(ATTR_K2);
        addAttribute(ATTR_K3);
        addAttribute(ATTR_TILT);
    }

    public double getAngle() {
        return getAttribute(ATTR_ANGLE);
    }

    public double getE1() {
        return getAttribute(ATTR_E1);
    }

    public double getE2() {
        return getAttribute(ATTR_E2);
    }

    public double getFint() {
        return getAttribute(ATTR_FINT);
    }

    public double getFintX() {
        return getAttribute(ATTR_FINTX);
    }

    public double getH1() {
        return getAttribute(ATTR_H1);
    }

    public double getH2() {
        return getAttribute(ATTR_H2);
    }

    public double getHGap() {
        return getAttribute(ATTR_HGAP);
    }

    public double getK0() {
        return getAttribute(ATTR_K0);
    }

    public double getK0S() {
        return getAttribute(ATTR_K0S);
    }

    public double getK1() {
        return getAttribute(ATTR_K1);
    }

    public double getK2() {
        return getAttribute(ATTR_K2);
    }

    public double getK3() {
        return getAttribute(ATTR_K3);
    }

    public double getTilt() {
        return getAttribute(ATTR_TILT);
    }

    public void setAngle(double angle) {
        setAttribute(ATTR_ANGLE, angle);
    }

    /* short name (e1) to use same name as madx */
    public void setE1(double e1) { // NOPMD by kaifox on 6/25/10 5:30 PM
        setAttribute(ATTR_E1, e1);
    }

    /* short name (e2) to use same name as madx */
    public void setE2(double e2) { // NOPMD by kaifox on 6/25/10 5:31 PM
        setAttribute(ATTR_E2, e2);
    }

    public void setFint(double fint) {
        setAttribute(ATTR_FINT, fint);
    }

    public void setFintX(double fintx) {
        setAttribute(ATTR_FINTX, fintx);
    }

    public void setH1(double h1) { // NOPMD by kaifox on 6/25/10 5:32 PM
        setAttribute(ATTR_H1, h1);
    }

    public void setH2(double h2) { // NOPMD by kaifox on 6/25/10 5:32 PM
        setAttribute(ATTR_H2, h2);
    }

    public void setHGap(double hgap) {
        setAttribute(ATTR_HGAP, hgap);
    }

    /* short name (k0) to use same name as madx */
    public void setK0(double k0) { // NOPMD by kaifox on 6/25/10 5:33 PM
        setAttribute(ATTR_K0, k0);
    }

    public void setK0S(double k0s) {
        setAttribute(ATTR_K0S, k0s);
    }

    /* PMD: short name to be consistent with MadX */
    public void setK1(double k1) { // NOPMD by kaifox on 10/6/10 6:47 PM
        setAttribute(ATTR_K1, k1);
    }

    /* PMD: short name to be consistent with MadX */
    public void setK2(double k2) { // NOPMD by kaifox on 10/6/10 6:47 PM
        setAttribute(ATTR_K2, k2);
    }

    /* PMD: short name to be consistent with MadX */
    public void setK3(double k3) { // NOPMD by kaifox on 10/6/10 6:47 PM
        setAttribute(ATTR_K3, k3);
    }

    public void setTilt(double tilt) {
        setAttribute(ATTR_TILT, tilt);
    }
}
