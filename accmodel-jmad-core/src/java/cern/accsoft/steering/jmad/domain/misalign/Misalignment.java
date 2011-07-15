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
 * $Id: Misalignment.java,v 1.2 2009-01-20 19:43:10 kfuchsbe Exp $
 * 
 * $Date: 2009-01-20 19:43:10 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.domain.misalign;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.util.bean.NamedBean;

/**
 * this class defines values, which describe a misalignment of one or more elements. When communicating with MadX this
 * always results in a set of <a href="http://mad.web.cern.ch/mad/error/error_align.html">EALIGN</a> commands.
 * 
 * @author kfuchsbe
 */
public class Misalignment implements NamedBean {

    /** the listeners */
    private final List<MisalignmentListener> listeners = new ArrayList<MisalignmentListener>();

    /**
     * DX: The misalignment in the x-direction for the entry of the beam element (default: 0 m). DX>0 displaces the
     * element in the positive x-direction
     */
    private Double deltaX = null;

    /**
     * DY: The misalignment in the y-direction for the entry of the beam element (default: 0 m). DY>0 displaces the
     * element in the positive y-direction
     */
    private Double deltaY = null;

    /**
     * DS: The misalignment in the s-direction for the entry of the beam element (default: 0 m). DS>0 displaces the
     * element in the positive s-direction
     */
    private Double deltaS = null;

    /**
     * DPHI: The rotation around the x-axis. A positive angle gives a greater x-coordinate for the exit than for the
     * entry (default: 0 rad).
     */
    private Double deltaPhi = null;

    /**
     * DTHETA: The rotation around the y-axis according to the right hand rule (default: 0 rad).
     */
    private Double deltaTheta = null;

    /**
     * DPSI: The rotation around the s-axis according to the right hand rule (default: 0 rad).
     */
    private Double deltaPsi = null;

    //
    // the following values only apply to monitors
    //

    /**
     * MREX: The horizontal read error for a monitor. This is ignored if the element is not a monitor If MREX>0 the
     * reading for x is too high (default: 0 m).
     */
    private Double monitorReadErrorX = null;

    /**
     * MREY: The vertical read error for a monitor. This is ignored if the element is not a monitor If MREY>0, the
     * reading for y is too high (default: 0 m).
     */
    private Double monitorReadErrorY = null;

    /**
     * MSCALX: The relative horizontal scaling error for a monitor. This is ignored if the element is not a monitor. If
     * MSCALX>0 the reading for x is too high (default: 0). A value of 0.5 implies the actual reading is multiplied by
     * 1.5.
     */
    private Double monitorScalingErrorX = null;

    /**
     * MSCALY: The relative vertical scaling error for a monitor. This is ignored if the element is not a monitor. If
     * MSCALY>0 the reading for y is too high (default: 0). A value of -0.3 implies the actual reading is multiplied by
     * 0.7.
     */
    private Double monitorScalingErrorY = null;

    /**
     * AREX: The misalignment in the x-direction for the entry of an aperture limit (default: 0 m). AREX>0 displaces the
     * element in the positive x-direction
     */
    private Double apertureErrorX = null;

    /**
     * AREY: The misalignment in the y-direction for the entry of an aperture limit (default: 0 m). AREY>0 displaces the
     * element in the positive y-direction
     */
    private Double apertureErrorY = null;

    /**
     * the name of the misalignment
     */
    private final String name;

    /**
     * the constructor-which needs a name
     * 
     * @param name the name
     */
    public Misalignment(String name) {
        this.name = name;
    }

    /*
     * Getters and setters
     */

    /**
     * @return the deltaX
     */
    public final Double getDeltaX() {
        return deltaX;
    }

    /**
     * @param deltaX the deltaX to set
     */
    public final void setDeltaX(Double deltaX) {
        this.deltaX = deltaX;
        fireChangedValues();
    }

    /**
     * @return the deltaY
     */
    public final Double getDeltaY() {
        return deltaY;
    }

    /**
     * @param deltaY the deltaY to set
     */
    public final void setDeltaY(Double deltaY) {
        this.deltaY = deltaY;
        fireChangedValues();
    }

    /**
     * @return the deltaS
     */
    public final Double getDeltaS() {
        return deltaS;
    }

    /**
     * @param deltaS the deltaS to set
     */
    public final void setDeltaS(Double deltaS) {
        this.deltaS = deltaS;
        fireChangedValues();
    }

    /**
     * @return the deltaPhi
     */
    public final Double getDeltaPhi() {
        return deltaPhi;
    }

    /**
     * @param deltaPhi the deltaPhi to set
     */
    public final void setDeltaPhi(Double deltaPhi) {
        this.deltaPhi = deltaPhi;
        fireChangedValues();
    }

    /**
     * @return the deltaTheta
     */
    public final Double getDeltaTheta() {
        return deltaTheta;
    }

    /**
     * @param deltaTheta the deltaTheta to set
     */
    public final void setDeltaTheta(Double deltaTheta) {
        this.deltaTheta = deltaTheta;
        fireChangedValues();
    }

    /**
     * @return the deltaPsi
     */
    public final Double getDeltaPsi() {
        return deltaPsi;
    }

    /**
     * @param deltaPsi the deltaPsi to set
     */
    public final void setDeltaPsi(Double deltaPsi) {
        this.deltaPsi = deltaPsi;
        fireChangedValues();
    }

    /**
     * @return the monitorReadErrorX
     */
    public final Double getMonitorReadErrorX() {
        return monitorReadErrorX;
    }

    /**
     * @param monitorReadErrorX the monitorReadErrorX to set
     */
    public final void setMonitorReadErrorX(Double monitorReadErrorX) {
        this.monitorReadErrorX = monitorReadErrorX;
        fireChangedValues();
    }

    /**
     * @return the monitorReadErrorY
     */
    public final Double getMonitorReadErrorY() {
        return monitorReadErrorY;
    }

    /**
     * @param monitorReadErrorY the monitorReadErrorY to set
     */
    public final void setMonitorReadErrorY(Double monitorReadErrorY) {
        this.monitorReadErrorY = monitorReadErrorY;
        fireChangedValues();
    }

    /**
     * @return the monitorScalingErrorX
     */
    public final Double getMonitorScalingErrorX() {
        return monitorScalingErrorX;
    }

    /**
     * @param monitorScalingErrorX the monitorScalingErrorX to set
     */
    public final void setMonitorScalingErrorX(Double monitorScalingErrorX) {
        this.monitorScalingErrorX = monitorScalingErrorX;
        fireChangedValues();
    }

    /**
     * @return the monitorScalingErrorY
     */
    public final Double getMonitorScalingErrorY() {
        return monitorScalingErrorY;
    }

    /**
     * @param monitorScalingErrorY the monitorScalingErrorY to set
     */
    public final void setMonitorScalingErrorY(Double monitorScalingErrorY) {
        this.monitorScalingErrorY = monitorScalingErrorY;
        fireChangedValues();
    }

    /**
     * @return the apertureErrorX
     */
    public final Double getApertureErrorX() {
        return apertureErrorX;
    }

    /**
     * @param apertureErrorX the apertureErrorX to set
     */
    public final void setApertureErrorX(Double apertureErrorX) {
        this.apertureErrorX = apertureErrorX;
        fireChangedValues();
    }

    /**
     * @return the apertureErrorY
     */
    public final Double getApertureErrorY() {
        return apertureErrorY;
    }

    /**
     * @param apertureErrorY the apertureErrorY to set
     */
    public final void setApertureErrorY(Double apertureErrorY) {
        this.apertureErrorY = apertureErrorY;
        fireChangedValues();
    }

    /*
     * methods concerning listeners
     */

    /**
     * notify the listeners, that values have changed
     */
    private void fireChangedValues() {
        for (MisalignmentListener listener : this.listeners) {
            listener.changedValues(this);
        }
    }

    /**
     * @param listener the listener to add
     */
    public void addListener(MisalignmentListener listener) {
        this.listeners.add(listener);
    }

    /**
     * @param listener the listener to remove
     */
    public void removeListener(MisalignmentListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
