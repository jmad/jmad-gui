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

/**
 * 
 */
package cern.accsoft.steering.jmad.domain.result.match.input;

import java.util.List;
import java.util.Map;

/**
 * Thi interface which represents a general matching constraint for madx
 * 
 * @author muellerg
 */
public interface MatchConstraint {

    /**
     * @return true, if it is a global constraint
     */
    public abstract boolean isGlobal();

    /**
     * @return MadxName-Value Pairs for all defined Parameters of this constraint
     */
    public abstract Map<String, Double> getParameterSettings();

    /**
     * @return Madx values-command names to read the resulting value of the constraints after matching...
     *         <p>
     *         for local matching, if a range is specified, only names for first and last element of the range are
     *         returned
     */
    public abstract List<String> getReadoutNames();

}
