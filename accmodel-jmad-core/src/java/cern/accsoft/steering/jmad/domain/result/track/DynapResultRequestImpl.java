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
 * Standard implementation of DynapResultRequest
 * 
 * @author xbuffat
 */

public class DynapResultRequestImpl extends AbstractTrackResultRequest implements DynapResultRequest {

    private boolean fastTune;
    private double lyapunov;
    private boolean orbit;

    public DynapResultRequestImpl(RelativeParticleDistribution dist) {
        super(dist);
        this.setFastTune(false);
        this.setLyapunov(1.0E-7);
        this.setOrbit(true);
    }

    @Override
    public boolean isFastTune() {
        return this.fastTune;
    }

    @Override
    public double getLyapunov() {
        return this.lyapunov;
    }

    @Override
    public boolean isOrbit() {
        return this.orbit;
    }

    @Override
    public void setFastTune(boolean fastTune) {
        this.fastTune = fastTune;

    }

    @Override
    public void setLyapunov(double lyapunov) {
        this.lyapunov = lyapunov;

    }

    @Override
    public void setOrbit(boolean orbit) {
        this.orbit = orbit;

    }

}
