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

package cern.accsoft.steering.jmad.domain.result.track;

import cern.accsoft.steering.jmad.domain.track.RelativeParticleDistribution;

/**
 * Defines the information wanted in the DynapResult
 * 
 * @author xbuffat
 */

public interface DynapResultRequest {

    /**
     * @param turns Number of turns to track
     */
    void setTurns(Integer turns);

    /**
     * @return Number of turns to track
     */
    Integer getTurns();

    /**
     * @param fastTune If true, the only tunes of the particle tracked is returned
     */
    void setFastTune(boolean fastTune);

    /**
     * @return If true, the only tunes of the particle tracked is returned
     */
    boolean isFastTune();

    /**
     * @param Lyapunov The launch distance between two companion particles added to the x coordinate
     */
    void setLyapunov(double Lyapunov);

    /**
     * @return The launch distance between two companion particles added to the x coordinate
     */
    double getLyapunov();

    /**
     * @param orbit If set, the flag orbit is true during the tracking and its initialization
     */
    void setOrbit(boolean orbit);

    /**
     * @return If set, the flag orbit is true during the tracking and its initialization
     */
    boolean isOrbit();

    /**
     * @param apertureLimited if true, the particle reaching aperture limit are considered lost.
     */
    void setApertureLimited(boolean apertureLimited);

    /**
     * @return if true, the particle reaching aperture limit are considered lost.
     */
    boolean isApertureLimited();

    /**
     * Particles reaching these values are considered lost if this.isApertureLimited()
     * 
     * @param x
     * @param px
     * @param y
     * @param py
     * @param t
     * @param pt
     */
    void setApertureLimitation(double x, double px, double y, double py, double t, double pt);

    /**
     * @return Particles reaching these values of x,px,y,py,t,pt are considered lost if this.isApertureLimited()
     */
    Double[] getApertureLimitation();

    /**
     * @param relatvieParticleDistribution Initial coordinates of the particles to be tracked
     */
    void setRelativeParticleDisctribution(RelativeParticleDistribution relatvieParticleDistribution);

    /**
     * @return Initial coordinates of the particles to be tracked
     */
    RelativeParticleDistribution getRelativeParticleDistribution();

}
