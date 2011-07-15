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

package cern.accsoft.steering.jmad.kernel.cmd.param;

/**
 * this interface represents all kind of possible parameters to madx-commands
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface Parameter {

    /**
     * has to be implemented in subclass to return the correct "name=value" - syntax.
     * 
     * @return a string representing the correct syntax for MadX
     */
    public abstract String compose();

    /**
     * has to be implemented in subclass to determine if the parameter is set or not.
     * 
     * @return true, if set, false otherwise.
     */
    public abstract boolean isSet();

}
