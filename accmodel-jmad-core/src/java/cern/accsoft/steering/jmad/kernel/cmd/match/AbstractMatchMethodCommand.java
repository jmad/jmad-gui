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

import cern.accsoft.steering.jmad.domain.result.match.methods.MatchMethod;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * @author muellerg
 */
public abstract class AbstractMatchMethodCommand extends AbstractCommand {

    /** the matching method */
    private final MatchMethod matchMethod;

    public AbstractMatchMethodCommand(MatchMethod matchMethod) {
        this.matchMethod = matchMethod;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<Integer>("calls", this.matchMethod.getCalls()));
        parameters.add(new GenericParameter<Double>("tolerance", this.matchMethod.getTolerance()));

        parameters.addAll(getSpecialParameters());
        return parameters;
    }

    /**
     * has to be implemented by subclass if the matching method has special parameters
     * 
     * @return the commands;
     */
    protected List<Parameter> getSpecialParameters() {
        return new ArrayList<Parameter>();
    }

    protected MatchMethod getMatchMethod() {
        return this.matchMethod;
    }

}
