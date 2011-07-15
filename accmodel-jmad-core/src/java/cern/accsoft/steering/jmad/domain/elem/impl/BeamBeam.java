// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.jmad.domain.elem.impl;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;

/**
 * Represents a beambeam element in Madx possible attribute
 * SIGX=real,SIGY=real,XMA=real,YMA=real,CHARGE=real,BBSHAPE=int,WIDTH=real,BBDIR=int A beam-beam element requires the
 * particle energy (ENERGY) and the particle charge (CHARGE) as well as the number of particles per bunch (NPART) to be
 * set by a BEAM command before any calculations are performed
 * 
 * @author xbuffat
 */

public class BeamBeam extends AbstractElement {
    /* SIGX: The horizontal extent of the opposite beam (default: 1 m). Meaning depends on parameter BBSHAPE. */
    private static final String ATTR_SIGX = "sigx";
    /* SIGY: The vertical extent of the opposite beam (default: 1 m). Meaning depends on parameter BBSHAPE. */
    private static final String ATTR_SIGY = "sigy";
    /* XMA: The horizontal displacement of the opposite beam with respect to the ideal orbit (default: 0 m). */
    private static final String ATTR_XMA = "xma";
    /* YMA: The vertical displacement of the opposite beam with respect to the ideal orbit (default: 0 m). */
    private static final String ATTR_YMA = "yma";
    /* CHARGE: The charge of particles in the opposite beam in elementary charges. It is set by (default CHARGE=1). */
    private static final String ATTR_CHARGE = "charge";
    /*
     * BBSHAPE: The parameter to choose the radial density shape of the opposite beam (default: 1) BBSHAPE=1: Gaussian
     * shape (default), SIGX/SIGY: standard deviation in vertical/horizontal direction. BBSHAPE=2: trapezoidal shape,
     * SIGX/SIGY: half width of density profile. BBSHAPE=3: hollow-parabolic shape, SIGX/SIGY: distance from the centre
     * to the maximum of the parabolic density profile in vertical/horizontal direction.
     */
    private static final String ATTR_BBSHAPE = "bbshape";
    /* WIDTH: The relative extent of the edge region */
    private static final String ATTR_WIDTH = "width";
    /*
     * BBDIR: The parameter to choose the direction of motion of the opposite beam relative to the beam considered.
     * (default: -1). BBDIR=-1: Beams move in the opposite direction BBDIR=0: Opposite beam does not move BBDIR=1: Beams
     * move in the same direction
     */
    private static final String ATTR_BBDIR = "bbdir";

    public BeamBeam(MadxElementType madxElementType, String name) {
        super(madxElementType, name);
        this.addAttribute(ATTR_SIGX);
        this.addAttribute(ATTR_SIGY);
        this.addAttribute(ATTR_XMA);
        this.addAttribute(ATTR_YMA);
        this.addAttribute(ATTR_CHARGE);
        this.addAttribute(ATTR_BBSHAPE);
        this.addAttribute(ATTR_WIDTH);
        this.addAttribute(ATTR_BBDIR);
    }

    public double getSigX() {
        return this.getAttribute(ATTR_SIGX);
    }

    public double getSigY() {
        return this.getAttribute(ATTR_SIGY);
    }

    public double getDisplacementX() {
        return this.getAttribute(ATTR_XMA);
    }

    public double getDisplacementY() {
        return this.getAttribute(ATTR_YMA);
    }

    public double getCharge() {
        return this.getAttribute(ATTR_CHARGE);
    }

    public double getShape() {
        return this.getAttribute(ATTR_BBSHAPE);
    }

    public double getWidth() {
        return this.getAttribute(ATTR_WIDTH);
    }

    public double getDirection() {
        return this.getAttribute(ATTR_BBDIR);
    }
}
