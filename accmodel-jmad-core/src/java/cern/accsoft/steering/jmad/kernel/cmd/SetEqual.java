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

package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SetEqual extends AbstractJMadExecutable implements Command {
    private final String valueName;
    private final Double value;

    public SetEqual(String valueName, Double value) {
        this.valueName = valueName;
        this.value = value;
    }

    @Override
    public String compose() {
        return valueName + " = " + value + ";";
    }

    @Override
    public String getName() {
        return this.valueName;
    }

    @Override
    public List<Parameter> getParameters() {
        /*
         * XXX check if really necessary, that this implements a command
         */
        List<Parameter> retVal = new ArrayList<Parameter>();
        retVal.add(new GenericParameter<Double>(this.valueName, this.value));
        return retVal;
    }
}
