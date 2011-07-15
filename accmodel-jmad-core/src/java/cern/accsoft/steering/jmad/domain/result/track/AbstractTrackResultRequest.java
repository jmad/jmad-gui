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
 * This class provides methods common to ResultRequests involving tracking.
 * 
 * @author xbuffat
 */

public class AbstractTrackResultRequest {

    private boolean apertureLimited;
    private Double[] apertureLimitation;
    private Integer turns;
    private RelativeParticleDistribution relativeParticleDistribution;

    public AbstractTrackResultRequest(RelativeParticleDistribution dist) {
        this.relativeParticleDistribution = dist;
        this.setApertureLimited(false);
        this.setApertureLimitation(0, 0, 0, 0, 0, 0);
        this.setTurns(1);
    }

    public void setApertureLimitation(double x, double px, double y, double py, double t, double pt) {
        this.apertureLimitation = new Double[6];
        this.apertureLimitation[0] = x;
        this.apertureLimitation[1] = px;
        this.apertureLimitation[2] = y;
        this.apertureLimitation[3] = py;
        this.apertureLimitation[4] = t;
        this.apertureLimitation[5] = pt;

    }

    public void setTurns(Integer turns) {
        this.turns = turns;

    }

    public Double[] getApertureLimitation() {
        return this.apertureLimitation;
    }

    public Integer getTurns() {
        return this.turns;
    }

    public boolean isApertureLimited() {
        return this.apertureLimited;
    }

    public void setApertureLimited(boolean apertureLimited) {
        this.apertureLimited = apertureLimited;

    }

    public RelativeParticleDistribution getRelativeParticleDistribution() {
        return this.relativeParticleDistribution;
    }

    public void setRelativeParticleDisctribution(RelativeParticleDistribution relativeParticleDistribution) {
        this.relativeParticleDistribution = relativeParticleDistribution;

    }

}
