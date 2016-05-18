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

/*
 * $Id: Corrector.java,v 1.2 2008-09-25 08:03:04 kfuchsbe Exp $
 * 
 * $Date: 2008-09-25 08:03:04 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.elem.impl;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.elem.MadxElementType;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * Represents an orbit corrector in JMad. This represents all the element of the types <a
 * href="http://mad.web.cern.ch/mad/Introduction/kickers.html">HKICKER, VKICKER, KICKER and TKICKER</a> in MadX.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class Corrector extends AbstractElement {
    /** the logger */
    private static final Logger LOGGER = Logger.getLogger(Corrector.class);

    /** The kick angle for either horizontal or vertical correctors. [rad] */
    public static final String ATTR_KICK = "kick";

    /** The horizontal kick angle for a corrector in both planes [rad] */
    public static final String ATTR_HKICK = "hkick";

    /** The vertical kick angle for a corrector in both planes [rad] */
    public static final String ATTR_VKICK = "vkick";

    /** The tilt of the kicker */
    public static final String ATTR_TILT = "tilt";

    /**
     * The constructor. It adds the specific attributes to the superclass.
     * 
     * @param madxElementType the type that represents this specific element in MadX
     * @param name the Name of the kicker
     */
    public Corrector(MadxElementType madxElementType, String name) {
        super(madxElementType, name);

        addAttribute(ATTR_KICK);
        addAttribute(ATTR_HKICK);
        addAttribute(ATTR_VKICK);
        addAttribute(ATTR_TILT);
    }

    /**
     * set the tilt of the element
     * 
     * @param tilt the tilt to set
     */
    public void setTilt(double tilt) {
        setAttribute(ATTR_TILT, tilt);
    }

    /**
     * @return the tilt of the element
     */
    public double getTilt() {
        return getAttribute(ATTR_TILT);
    }

    /**
     * set the kick for this corrector
     * 
     * @param kick the kick to set
     */
    public void setKick(double kick) {
        setAttribute(ATTR_KICK, kick);
    }

    /**
     * set the horizontal kick for this corrector
     * 
     * @param hKick the kick to set
     */
    public void setHKick(double hKick) {
        setAttribute(ATTR_HKICK, hKick);
    }

    /**
     * set the vertical kick for this corrector
     * 
     * @param vKick the kick to set
     */
    public void setVKick(double vKick) {
        setAttribute(ATTR_VKICK, vKick);
    }

    /**
     * @return the kick for a one-plane corrector
     */
    public double getKick() {
        return getAttribute(ATTR_KICK);
    }

    /**
     * @return the horizontal kick for a two-plane corrector
     */
    public double getHKick() {
        return getAttribute(ATTR_HKICK);
    }

    /**
     * @return the vertical kick for a two-plane corrector
     */
    public double getVKick() {
        return getAttribute(ATTR_VKICK);
    }

    /**
     * sets the corrector kick to the according attribute, depending on the plane
     * 
     * @param plane the plane in which to kick
     * @param kickValue the value to set for the kick
     */
    public final void setKick(JMadPlane plane, double kickValue) {
        String kickAttributeName = getKickAttributName(plane);
        if (kickAttributeName == null) {
            LOGGER.warn("cannot set kick in plane '" + plane + "' to corrector of type '" + getMadxElementType().name()
                    + "'.");
        } else {
            setAttribute(kickAttributeName, kickValue);
        }
    }

    /**
     * retrieve the current kick-value for given plane.
     * 
     * @param plane the {@link JMadPlane} for which to retrieve the value
     * @return the current kick-value
     */
    public final double getKick(JMadPlane plane) {
        String kickAttributeName = getKickAttributName(plane);
        Double kick = null;
        if (kickAttributeName != null) {
            kick = getAttribute(kickAttributeName);
        }

        if (kick == null) {
            LOGGER.warn("cannot get kick in plane '" + plane + "' from corrector of type '"
                    + getMadxElementType().name() + "' -> returning 0.");
            return 0.0;
        } else {
            return kick.doubleValue();
        }
    }

    /**
     * find out which attribute to use in order to get/set kicks in the given plane.
     * 
     * @param plane the plane for which to get/set the kick
     * @return the name of the attribute or null, if this makes no sense.
     */
    private String getKickAttributName(JMadPlane plane) {
        if (JMadPlane.H.equals(plane)) {
            if (MadxElementType.HKICKER.equals(getMadxElementType())) {
                return ATTR_KICK;
            } else if (MadxElementType.KICKER.equals(getMadxElementType())) {
                return ATTR_HKICK;
            } else {
                return null;
            }
        } else if (JMadPlane.V.equals(plane)) {
            if (MadxElementType.VKICKER.equals(getMadxElementType())) {
                return ATTR_KICK;
            } else if (MadxElementType.KICKER.equals(getMadxElementType())) {
                return ATTR_VKICK;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
