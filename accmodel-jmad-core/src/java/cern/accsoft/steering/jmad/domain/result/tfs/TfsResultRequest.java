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

package cern.accsoft.steering.jmad.domain.result.tfs;

import java.util.List;

import cern.accsoft.steering.jmad.domain.var.TwissVariable;

/**
 * Defines which madx-variables shall be contained in the {@link TfsResult} after the twiss.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface TfsResultRequest {

    /**
     * @return the requeset resultVariables.
     */
    public abstract List<TwissVariable> getResultVariables();

    /**
     * @return the requested element-filters.
     */
    public abstract List<String> getElementPattern();

    /**
     * @return the requested element-classes
     */
    public abstract List<String> getElementClasses();

    /**
     * @return <code>true</code>, if it is required to run a ptc-twiss to get the variables
     */
    public abstract boolean needsPtcTwiss();
}
