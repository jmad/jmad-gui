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

package cern.accsoft.steering.jmad.domain.result.match.input;

import cern.accsoft.steering.jmad.domain.knob.MadxParameter;

/**
 * the interface for a matchin variation parameter
 * 
 * @author muellerg
 */
public interface MadxVaryParameter {

    /**
     * returns the name of the parameter. This can be e.g. a name of a strength or the attribute of an element (e.g.
     * MCIAH->kick)
     * 
     * @return the (madx) name of the parameter to vary
     */
    public abstract String getName();

    /**
     * The approximate initial step size for varying the parameter. If the step is not entered, MAD tries to find a
     * reasonable step, but this may not always work.
     * 
     * @return the step to use.
     */
    public abstract Double getStep();

    /**
     * Lower limit for the parameter (optional)
     * 
     * @return the lower limit or null if not set
     */
    public abstract Double getLower();

    /**
     * Upper limit for the parameter (optional).
     * 
     * @return the upper limit or null if not set
     */
    public abstract Double getUpper();

    /**
     * @return the basis Parameter used for varying
     */
    public abstract MadxParameter getMadxParameter();
}
