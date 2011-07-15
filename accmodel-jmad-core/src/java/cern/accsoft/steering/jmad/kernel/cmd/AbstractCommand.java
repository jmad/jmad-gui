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
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public abstract class AbstractCommand extends AbstractJMadExecutable implements Command {

    @Override
    public final String compose() {
        StringBuffer command = new StringBuffer(getName());
        List<Parameter> parameters = getParameters();

        for (int i = 0; i < parameters.size(); i++) {
            Parameter param = parameters.get(i);
            if (param.isSet()) {
                command.append(", ");
                command.append(param.compose());
            }
        }
        command.append(';');

        return command.toString();
    }

    @Override
    public String toString() {
        return compose();
    }

    /**
     * this may be overridden if the command has parameters.
     */
    @Override
    public List<Parameter> getParameters() {
        return new ArrayList<Parameter>();
    }

}
