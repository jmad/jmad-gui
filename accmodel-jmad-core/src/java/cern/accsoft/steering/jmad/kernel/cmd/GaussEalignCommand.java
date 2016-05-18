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

/*
 * $Id: EalignCommand.java,v 1.1 2009-01-15 11:46:26 kfuchsbe Exp $
 * 
 * $Date: 2009-01-15 11:46:26 $ $Revision: 1.1 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.misalign.Misalignment;
import cern.accsoft.steering.jmad.kernel.cmd.param.FunctionParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * This class represents the command for the misaligning one or more elements with random tgauss (for DX and DY) values.
 * If gaussDistribution value is 0 default tgauss() option is provided. <br>
 * <br>
 * TODO add more random parameters
 * 
 * @author agorzaws
 */
public class GaussEalignCommand extends AbstractCommand {

    /** the name of the command */
    private static final String CMD_NAME = "ealign";

    /** the misalignment values */
    private final Misalignment misalignment;
    private final double gaussDistribuition;

    /**
     * The default constructor.
     * 
     * @param misalignment the misalignment to apply.
     * @param gaussDistribuition to include
     */
    public GaussEalignCommand(Misalignment misalignment, double gaussDistribuition) {
        this.misalignment = misalignment;
        this.gaussDistribuition = gaussDistribuition;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        /*
         * define the mapping of the member-vars to the parameter names
         */

        /*
         * EALIGN, DX=real,DY=real,DS=real, DPHI=real,DTHETA=real,DPSI=real, MREX=real,MREY=real,
         * MSCALX=real,MSCALY=real, AREX=real,AREY=real;
         */

        // parameters.add(new GenericParameter<Double>("dx", misalignment.getDeltaX()));
        // parameters.add(new GenericParameter<Double>("dy", misalignment.getDeltaY()));
        parameters.add(new FunctionParameter("dx", "tgauss", misalignment.getDeltaX(), gaussDistribuition));
        parameters.add(new FunctionParameter("dy", "tgauss", misalignment.getDeltaY(), gaussDistribuition));

        // TODO add randomness into the rest of the parameters
        parameters.add(new GenericParameter<Double>("ds", misalignment.getDeltaS()));
        parameters.add(new GenericParameter<Double>("dphi", misalignment.getDeltaPhi()));
        parameters.add(new GenericParameter<Double>("dtheta", misalignment.getDeltaTheta()));
        parameters.add(new GenericParameter<Double>("dpsi", misalignment.getDeltaPsi()));
        parameters.add(new GenericParameter<Double>("mrex", misalignment.getMonitorReadErrorX()));
        parameters.add(new GenericParameter<Double>("mrey", misalignment.getMonitorReadErrorY()));
        parameters.add(new GenericParameter<Double>("mscalx", misalignment.getMonitorScalingErrorX()));
        parameters.add(new GenericParameter<Double>("mscaly", misalignment.getMonitorScalingErrorY()));
        parameters.add(new GenericParameter<Double>("arex", misalignment.getApertureErrorX()));
        parameters.add(new GenericParameter<Double>("arey", misalignment.getApertureErrorY()));

        return parameters;
    }

}
