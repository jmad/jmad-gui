// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
 * 
 ******************************************************************************/
// @formatter:on
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
