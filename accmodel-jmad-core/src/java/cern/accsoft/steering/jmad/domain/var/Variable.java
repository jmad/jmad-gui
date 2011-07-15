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

package cern.accsoft.steering.jmad.domain.var;

/**
 * This is the general interface for a variable. It simple has a name and a unit.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface Variable {

    /**
     * @return the name of the variable (can be arbitrary)
     */
    public abstract String getName();

    /**
     * @return a string, that represents the unit of the variable
     */
    public abstract String getUnit();

    /**
     * the type of the values which this variable represents
     * 
     * @return The class of the values
     */
    public abstract Class<?> getValueClass();

}
