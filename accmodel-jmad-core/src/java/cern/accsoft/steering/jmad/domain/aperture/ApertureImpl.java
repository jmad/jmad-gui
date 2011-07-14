// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.JMadConstants;
import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

/**
 * geometric aperture information for jmad
 * 
 * @author muellerg
 */
/*
 * XXX (KF) There is still a lot to refactor. (especially in the reader) This is just the first quick implementation
 * which is based on a copy of the classes from the online model
 */
public class ApertureImpl implements Aperture {

    private final List<ApertureSlice> apertureSlices = new ArrayList<ApertureSlice>();
    private final Map<String, Double> markerPos = new HashMap<String, Double>();

    private final List<String> missedMarkers = new ArrayList<String>();

    private double aperModelStart = Double.MAX_VALUE;
    private double aperModelEnd = 0.0;

    private enum Type {
        MIN {

            @Override
            public double getValue(ApertureSlice slice, JMadPlane plane) {
                return slice.getMin(plane);
            }

        },
        MAX {
            @Override
            public double getValue(ApertureSlice slice, JMadPlane plane) {
                return slice.getMax(plane);
            }
        };

        public abstract double getValue(ApertureSlice slice, JMadPlane plane);
    }

    @Override
    public List<Double> getMaxValues(JMadPlane plane, boolean aroundTrajectory) {
        return getMinMaxValues(Type.MAX, plane, aroundTrajectory);
    }

    @Override
    public List<Double> getMinValues(JMadPlane plane, boolean aroundTrajectory) {
        return getMinMaxValues(Type.MIN, plane, aroundTrajectory);
    }

    private List<Double> getMinMaxValues(Type type, JMadPlane plane, boolean aroundTrajectory) {
        List<Double> res = new ArrayList<Double>(apertureSlices.size());
        for (ApertureSlice slice : getApertureSlices()) {
            double value = type.getValue(slice, plane);

            if (aroundTrajectory) {
                value += slice.getPos(plane);
            }
            res.add(value);
        }
        return res;
    }

    @Override
    public List<Double> getSValues() {
        List<Double> res = new ArrayList<Double>(apertureSlices.size());
        for (ApertureSlice slice : getApertureSlices()) {
            res.add(slice.getS());
        }
        return res;

    }

    @Override
    public List<Double> getXminValues(boolean aroundTrajectory) {
        return getMinValues(JMadPlane.H, aroundTrajectory);
    }

    @Override
    public List<Double> getXmaxValues(boolean aroundTrajectory) {
        return getMaxValues(JMadPlane.H, aroundTrajectory);
    }

    @Override
    public List<Double> getYminValues(boolean aroundTrajectory) {
        return getMinValues(JMadPlane.V, aroundTrajectory);
    }

    @Override
    public List<Double> getYmaxValues(boolean aroundTrajectory) {
        return getMinValues(JMadPlane.V, aroundTrajectory);
    }

    public void addMarker(String name, double pos) {
        this.markerPos.put(name, new Double(pos));
    }

    public void addSlice(ApertureSlice newSlice) {

        // TODO: if previous aperture values == markerValue --> overwrite the
        // values with the own values...

        // first Slice to be added to AperModel
        if (this.apertureSlices.size() == 0) {
            this.apertureSlices.add(newSlice);
            this.aperModelStart = newSlice.getS();
            this.aperModelEnd = newSlice.getS();
            return;
        }

        // newSlice before first defined slice
        if (newSlice.getS() < this.aperModelStart) {
            this.apertureSlices.add(0, newSlice);
            this.aperModelStart = newSlice.getS();
            return;
        }

        // newSlice after last defined slice
        if (newSlice.getS() > this.aperModelEnd) {
            this.apertureSlices.add(newSlice);
            this.aperModelEnd = newSlice.getS();
            return;
        }

        // Check where the new slice should be added
        // always append after the last slice!!!
        int scliceNumber = 0;
        while (scliceNumber < this.apertureSlices.size()) {
            if (this.apertureSlices.get(scliceNumber).getS() <= newSlice.getS()) {
                scliceNumber++;
                continue;
            }

            break;
        }

        // add Slice to Container
        this.apertureSlices.add(scliceNumber, newSlice);
    }

    /**
     * this list will be changed by the reader!
     * 
     * @return the list of missed markers
     */
    /* XXX (KF) not nice to change a list outside the class */
    public List<String> getMissedMarkers() {
        return missedMarkers;
    }

    public Double getMarkerPos(String markerName) {
        return this.markerPos.get(markerName.toUpperCase(JMadConstants.DEFAULT_LOCALE));
    }

    public List<ApertureSlice> getApertureSlices() {
        return this.apertureSlices;
    }

    public boolean isIndexLoaded() {
        return this.markerPos.size() > 0;
    }

}
