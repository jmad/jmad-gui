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
package cern.accsoft.steering.jmad.domain.aperture;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * Represents one slice of aperture in the aperture model. Such a slice is not necessarily equivalent with one element.
 * In the contrary on element might have many slices.
 * <p>
 * This class contains the original values as well as the calculated min/max values for the both planes. Additionaly the
 * positions for a so-called reference trajectory are stored, around which the aperture can later be centered.
 * 
 * @author kaifox
 */
public class ApertureSlice {

    private static final double MAX_APERTURE = 0.2;

    private AperType type;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private double aper1;
    private double aper2;
    private double aper3;
    private double aper4;
    private double posX;
    private double posY;
    private double posS;

    /**
     * Create a new ApertureSlice and calculate the Aperture Extrema
     * 
     * @param type {@link AperType} of this Element
     * @param aper1 Aperture Value 1
     * @param aper2 Aperture Value 2
     * @param aper3 Aperture Value 3
     * @param aper4 Aperture Value 4
     * @param pos Position of this Element in the Sequence
     */
    public ApertureSlice(AperType type, double aper1, double aper2, double aper3, double aper4, double pos) {
        this.posS = pos;
        this.type = type;

        this.setApertureValues(aper1, aper2, aper3, aper4);
    }

    /**
     * Create a new ApertureSlice only defined by its Type and Position
     * 
     * @param type {@link AperType} of this Element
     * @param pos Position of this Element in the Sequence
     */
    public ApertureSlice(AperType type, double pos) {
        this.posS = pos;
        this.type = type;
    }

    public double getAper1() {
        return aper1;
    }

    public double getAper2() {
        return aper2;
    }

    public double getAper3() {
        return aper3;
    }

    public double getAper4() {
        return aper4;
    }

    /**
     * Calculate the Aperture Extrema for the Aperture Slice according to the type and the 4 Aperture Values
     * 
     * @param aper1 the value of the madx-variable aper1
     * @param aper2 the value of the madx-variable aper2
     * @param aper3 the value of the madx-variable aper3
     * @param aper4 the value of the madx-variable aper4
     */
    public final void setApertureValues(double aper1, double aper2, double aper3, double aper4) {

        // Ensure visibility in Plots
        this.aper1 = (aper1 > MAX_APERTURE ? MAX_APERTURE : aper1);
        this.aper2 = (aper2 > MAX_APERTURE ? MAX_APERTURE : aper2);
        this.aper3 = (aper3 > MAX_APERTURE ? MAX_APERTURE : aper3);
        this.aper4 = (aper4 > MAX_APERTURE ? MAX_APERTURE : aper4);

        switch (this.type) {
        case RACETRACK:
            this.xMin = -aper1 - aper3;
            this.xMax = +aper1 + aper3;
            this.yMin = -aper2 - aper3;
            this.yMax = +aper2 + aper3;
            break;
        case RECTELLIPSE:
            this.xMin = (-1.0) * Math.min(aper1, aper3);
            this.xMax = Math.min(aper1, aper3);
            this.yMin = (-1.0) * Math.min(aper2, aper4);
            this.yMax = Math.min(aper2, aper4);
            break;
        case CIRCLE:
            this.xMin = (-1.0) * aper1;
            this.xMax = aper1;
            this.yMin = (-1.0) * aper1;
            this.yMax = aper1;
            break;
        case RECTANGLE:
            this.xMin = (-1.0) * aper1;
            this.xMax = aper1;
            this.yMin = (-1.0) * aper2;
            this.yMax = aper2;
            break;
        default:
        }
    }

    /**
     * Compares two aperture slices
     * 
     * @param actSlice aperture slice to compare to this one
     * @return <code>true</code> when type/position and all 4 ApertureValues are equal
     */
    public boolean sameAperInfo(ApertureSlice actSlice) {
        if (actSlice.type.equals(this.type) && actSlice.posS == this.posS && actSlice.aper1 == this.aper1
                && actSlice.aper2 == this.aper2 && actSlice.aper3 == this.aper3 && actSlice.aper4 == this.aper4) {
            return true;
        }

        return false;
    }

    public AperType getType() {
        return type;
    }

    public void setType(AperType type) {
        this.type = type;
    }

    public double getXmin() {
        return xMin;
    }

    public void setXmin(double xMin) {
        this.xMin = xMin;
    }

    public double getXmax() {
        return xMax;
    }

    public void setXmax(double xMax) {
        this.xMax = xMax;
    }

    public double getS() {
        return posS;
    }

    public void setS(double posS) {
        this.posS = posS;
    }

    public double getYmin() {
        return yMin;
    }

    public void setYmin(double yMin) {
        this.yMin = yMin;
    }

    public double getYmax() {
        return yMax;
    }

    public void setYmax(double yMax) {
        this.yMax = yMax;
    }

    public double getX() {
        return posX;
    }

    public void setX(double x) {
        this.posX = x;
    }

    public double getY() {
        return posY;
    }

    public void setY(double y) {
        this.posY = y;
    }

    /**
     * retrieves the trajectory-position for the given plane. This is the position around which the aperture can be
     * centered.
     * 
     * @param plane the plane for which to get the position
     * @return the position in the given plane
     */
    public double getPos(JMadPlane plane) {
        switch (plane) {
        case H:
            return getX();
        case V:
            return getY();
        default:
            throw new IllegalArgumentException("Unknown plane '" + plane + "'.");
        }
    }

    /**
     * returns max value of the aperture in the given plane.
     * 
     * @param plane the plane for which to get the value
     * @return the max aperture value in the given plane
     */
    public double getMax(JMadPlane plane) {
        switch (plane) {
        case H:
            return getXmax();
        case V:
            return getYmax();
        default:
            throw new IllegalArgumentException("Unknown plane '" + plane + "'.");
        }
    }

    /**
     * returns the aperture minimum value in the given plane
     * 
     * @param plane the plane for which to retrieve the minimum value
     * @return the aperture minimum value in the given plane
     */
    public double getMin(JMadPlane plane) {
        switch (plane) {
        case H:
            return getXmin();
        case V:
            return getYmin();
        default:
            throw new IllegalArgumentException("Unknown plane '" + plane + "'.");
        }
    }

}
