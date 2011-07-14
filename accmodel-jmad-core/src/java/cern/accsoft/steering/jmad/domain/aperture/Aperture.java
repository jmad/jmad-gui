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

import java.util.List;

import cern.accsoft.steering.jmad.domain.types.enums.JMadPlane;

public interface Aperture {

    /**
     * @return the s-Positions of the ApertureSequence
     */
    public List<Double> getSValues();

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the minimum values in the x-plane
     */
    public abstract List<Double> getXminValues(boolean aroundTrajectory);

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the maximum values in the x-plane
     */
    public abstract List<Double> getXmaxValues(boolean aroundTrajectory);

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the minimum values in the y plane
     */
    public abstract List<Double> getYminValues(boolean aroundTrajectory);

    /**
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the maximum values in the y plane
     */
    public abstract List<Double> getYmaxValues(boolean aroundTrajectory);

    /**
     * get the minimum values for the given plane
     * 
     * @param plane the plane for which to retrieve the minValues
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the minimum Values for the given plane
     */
    public abstract List<Double> getMinValues(JMadPlane plane, boolean aroundTrajectory);

    /**
     * get the maximum values for the given plane
     * 
     * @param plane the plane for which to retrieve the maximum Values
     * @param aroundTrajectory if <code>true</code> the aperture around the nominal beam trajectory is returned,
     *            otherwise the aperture as defined.
     * @return the maximum values for the given plane
     */
    public abstract List<Double> getMaxValues(JMadPlane plane, boolean aroundTrajectory);

}
