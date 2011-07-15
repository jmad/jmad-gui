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
package cern.accsoft.steering.jmad.kernel.cmd.match;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.result.match.methods.AbstractMatchMethod;
import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethodJacobian;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public class MatchMethodJacobianCommand extends AbstractMatchMethodCommand {

    /** the name of the command */
    private static final String CMD_NAME = "jacobian";

    public MatchMethodJacobianCommand(AbstractMatchMethod matchMethod) {
        super(matchMethod);
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    protected List<Parameter> getSpecialParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();

        if (getMatchMethod() instanceof MatchMethodJacobian) {
            MatchMethodJacobian method = (MatchMethodJacobian) getMatchMethod();
            parameters.add(new GenericParameter<Integer>("repeat", method.getRepeat()));

        }
        return parameters;

    }

}
