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

import cern.accsoft.steering.jmad.util.MadxVarType;

/**
 * This is the general interface for a variable. It simple has a name and a unit.
 * 
 * @author kfuchsbe
 */
public interface MadxVariable extends Variable {

    /**
     * retrieve the name of the variable. This must be a unique expression within the madx model.
     * 
     * @return the name of the variable.
     */
    public abstract String getMadxName();

    /**
     * @return the type (String/Double) of the variable
     */
    public abstract MadxVarType getVarType();

}
