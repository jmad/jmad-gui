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

package cern.accsoft.steering.jmad.domain.knob.strength;

import cern.accsoft.steering.jmad.domain.knob.Knob;
import cern.accsoft.steering.jmad.domain.knob.MadxParameter;

/**
 * this class represents a simple name-value pair for madx.
 * 
 * @author kfuchsbe
 */
public interface Strength extends Knob, MadxParameter {
    /* nothing special at the moment */
}
