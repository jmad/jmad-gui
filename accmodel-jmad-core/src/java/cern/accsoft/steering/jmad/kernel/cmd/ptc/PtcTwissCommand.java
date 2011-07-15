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

package cern.accsoft.steering.jmad.kernel.cmd.ptc;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditions;
import cern.accsoft.steering.jmad.kernel.cmd.AbstractCommand;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * this class represents a ptc-twiss command
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class PtcTwissCommand extends AbstractCommand {

    /** the name of the command */
    private static final String CMD_NAME = "ptc_twiss";

    /*
     * TODO the following parameters should be either parameters, or integrated into the Twiss - class (maybe derive!?)
     */
    private static final int MAP_ORDER = 2;
    private static final int PHASE_SPACE_DIM = 5;

    /**
     * the twiss values
     */
    private final TwissInitialConditions twiss;

    public PtcTwissCommand(TwissInitialConditions twiss) {
        this.twiss = twiss;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    /* TODO implement all options! */
    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<Double>("deltap", twiss.getDeltap()));

        /*
         * the initial conditions must not be set, if we want to calc the closed orbit solution
         */
        if (!twiss.isClosedOrbit()) {
            parameters.add(new GenericParameter<Double>("betx", twiss.getBetx()));
            parameters.add(new GenericParameter<Double>("alfx", twiss.getAlfx()));
            parameters.add(new GenericParameter<Double>("bety", twiss.getBety()));
            parameters.add(new GenericParameter<Double>("alfy", twiss.getAlfy()));
            parameters.add(new GenericParameter<Double>("dx", twiss.getDx()));
            parameters.add(new GenericParameter<Double>("dy", twiss.getDy()));
            parameters.add(new GenericParameter<Double>("dpx", twiss.getDpx()));
            parameters.add(new GenericParameter<Double>("dpy", twiss.getDpy()));
            parameters.add(new GenericParameter<Double>("x", twiss.getX()));
            parameters.add(new GenericParameter<Double>("px", twiss.getPx()));
            parameters.add(new GenericParameter<Double>("y", twiss.getY()));
            parameters.add(new GenericParameter<Double>("py", twiss.getPy()));
            parameters.add(new GenericParameter<Double>("t", twiss.getT()));
            parameters.add(new GenericParameter<Double>("pt", twiss.getPt()));
            parameters.add(new GenericParameter<Double>("mux", twiss.getMux()));
            parameters.add(new GenericParameter<Double>("muy", twiss.getMuy()));
        }
        parameters.add(new GenericParameter<Boolean>("closed_orbit", twiss.isClosedOrbit()));
        parameters.add(new GenericParameter<Integer>("icase", PHASE_SPACE_DIM));
        parameters.add(new GenericParameter<Integer>("no", MAP_ORDER));

        if (getOutputFile() != null) {
            parameters.add(new GenericParameter<String>("file", getOutputFile().getAbsolutePath(), true));
        }

        return parameters;
    }

}
