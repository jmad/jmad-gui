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

import java.util.List;

import cern.accsoft.steering.jmad.domain.result.Result;
import cern.accsoft.steering.jmad.domain.var.enums.MadxDynapVariable;

/**
 * This interface allow to manage informations contained in the output of a dynap task. Information in the summary are
 * discarded.
 * 
 * @author xbuffat
 */

public interface DynapResult extends Result {

    /**
     * clear the result
     */
    void clear();

    /**
     * store a value associated to the variable
     * 
     * @param variable
     * @param value
     */
    void add(MadxDynapVariable variable, Double value);

    /**
     * retrieve data associated to the variable.
     * 
     * @param variable
     * @return all values for the variable
     */
    List<Double> get(MadxDynapVariable variable);

}
