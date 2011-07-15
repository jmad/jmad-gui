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

package cern.accsoft.steering.jmad.kernel.cmd.track;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.result.track.DynapResultRequest;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * Command DYNAP,TURNS=real, FASTUNE=logical,LYAPUNOV=real,MAXAPER:={..,..,..,..,..,..},ORBIT=logical;
 * 
 * @author xbuffat
 */

public class DynapCommand extends AbstractCommand {

    private static final String CMD_NAME = "dynap";

    private static final Logger LOGGER = Logger.getLogger(DynapCommand.class);

    private final DynapResultRequest dynapResultRequest;

    public DynapCommand(DynapResultRequest request) {
        this.dynapResultRequest = request;
    }

    @Override
    public String getName() {
        return DynapCommand.CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<Integer>("turns", this.getDynapResultRequest().getTurns()));
        parameters.add(new GenericParameter<Double>("lyapunov", this.getDynapResultRequest().getLyapunov()));
        parameters.add(new GenericParameter<Boolean>("fastune", this.getDynapResultRequest().isFastTune()));
        parameters.add(new GenericParameter<Boolean>("orbit", this.getDynapResultRequest().isOrbit()));
        if (this.getDynapResultRequest().isApertureLimited()) {
            LOGGER.warn("Aperture limitation is not avilable yet, no aperture limitation set");
        }

        return parameters;
    }

    public DynapResultRequest getDynapResultRequest() {
        return dynapResultRequest;
    }

}
