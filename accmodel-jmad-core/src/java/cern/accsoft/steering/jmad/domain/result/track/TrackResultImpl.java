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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.domain.result.ResultType;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTrackVariable;

/**
 * Standard implementation of TrackResult
 * 
 * @author xbuffat
 */

public class TrackResultImpl implements TrackResult {

    private Map<MadxTrackVariable, List<List<Double>>> data;

    public TrackResultImpl() {
        this.data = new HashMap<MadxTrackVariable, List<List<Double>>>();
        for (MadxTrackVariable variable : MadxTrackVariable.values()) {
            this.data.put(variable, new ArrayList<List<Double>>());
        }
    }

    @Override
    public ResultType getResultType() {
        return ResultType.TRACK_RESULT;
    }

    @Override
    public Integer getParticleCount() {
        return this.data.get(MadxTrackVariable.X).size();
    }

    @Override
    public void add(MadxTrackVariable variable, Integer particleNumber, Double value) {
        int particleIndex = particleNumber - 1;
        while (this.data.get(variable).size() <= particleIndex) {
            this.data.get(variable).add(new ArrayList<Double>());
        }

        this.data.get(variable).get(particleIndex).add(value);
    }

    @Override
    public List<Double> get(MadxTrackVariable variable, Integer particleIndex) {
        if (particleIndex < this.data.get(variable).size()) {
            return this.data.get(variable).get(particleIndex);
        } else {
            return new ArrayList<Double>();
        }
    }

    @Override
    public void clear() {
        for (MadxTrackVariable variable : MadxTrackVariable.values()) {
            this.data.get(variable).clear();
        }
    }
}
