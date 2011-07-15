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
import cern.accsoft.steering.jmad.domain.var.enums.MadxDynapVariable;

/**
 * Standard implementation of DynapResult
 * 
 * @author xbuffat
 */
public class DynapResultImpl implements DynapResult {

    Map<MadxDynapVariable, List<Double>> data;

    public DynapResultImpl() {
        this.data = new HashMap<MadxDynapVariable, List<Double>>();
        for (MadxDynapVariable var : MadxDynapVariable.values()) {
            this.data.put(var, new ArrayList<Double>());
        }
    }

    @Override
    public ResultType getResultType() {
        return ResultType.DYNAP_RESULT;
    }

    @Override
    public void add(MadxDynapVariable var, Double value) {
        this.data.get(var).add(value);

    }

    @Override
    public void clear() {
        for (MadxDynapVariable var : MadxDynapVariable.values()) {
            this.data.get(var).clear();
        }
    }

    @Override
    public List<Double> get(MadxDynapVariable variable) {
        return this.data.get(variable);
    }

}
