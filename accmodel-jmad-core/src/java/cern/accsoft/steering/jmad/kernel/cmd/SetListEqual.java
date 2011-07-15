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
import java.util.Map;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SetListEqual extends AbstractJMadExecutable implements Command {
    private final Map<String, Double> valuePairs;

    public SetListEqual(Map<String, Double> valuePairs) {
        this.valuePairs = valuePairs;
    }

    @Override
    public String compose() {
        StringBuffer cmd = new StringBuffer("");
        for (String valueName : this.valuePairs.keySet()) {
            cmd.append(valueName + " = " + this.valuePairs.get(valueName) + ";\n");
        }
        return cmd.toString();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public List<Parameter> getParameters() {
        return new ArrayList<Parameter>(0);
    }
}
