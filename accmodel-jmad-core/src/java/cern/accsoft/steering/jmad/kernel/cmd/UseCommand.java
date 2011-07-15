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

import cern.accsoft.steering.jmad.domain.machine.MadxRange;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class UseCommand extends AbstractCommand {
    private static final String CMD_NAME = "use";

    private static final String PARAM_NAME_PERIOD = "period";
    private static final String PARAM_NAME_RANGE = "range";

    /* the sequence to use */
    private String period = null;

    /* the range to use */
    private String range = null;

    public UseCommand(String period) {
        this(period, null);
    }

    public UseCommand(String period, MadxRange range) {
        this.period = period;
        if (range == null) {
            this.range = null;
        } else {
            this.range = range.getMadxString();
        }
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<String>(PARAM_NAME_PERIOD, period));
        parameters.add(new GenericParameter<String>(PARAM_NAME_RANGE, range));

        return parameters;
    }

}
