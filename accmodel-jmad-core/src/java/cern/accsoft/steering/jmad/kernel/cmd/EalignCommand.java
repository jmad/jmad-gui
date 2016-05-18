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
import cern.accsoft.steering.jmad.domain.var.enums.EalignVariables;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the command for the misaligning one or more elements. The elements have to be selected before by the
 * select command. EALIGN, DX=real,DY=real,DS=real, DPHI=real,DTHETA=real,DPSI=real, MREX=real,MREY=real,
 * MSCALX=real,MSCALY=real, AREX=real,AREY=real;
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class EalignCommand extends AbstractCommand {

    /** the name of the command */
    private static final String CMD_NAME = "ealign";

    /** the misalignment values */
    private final Misalignment misalignment;

    /**
     * The default constructor.
     * 
     * @param misalignment the misalignment to apply.
     */
    public EalignCommand(Misalignment misalignment) {
        this.misalignment = misalignment;
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
        parameters.add(new GenericParameter<Double>(EalignVariables.DX.getName(), misalignment.getDeltaX()));
        parameters.add(new GenericParameter<Double>(EalignVariables.DY.getName(), misalignment.getDeltaY()));
        parameters.add(new GenericParameter<Double>(EalignVariables.DS.getName(), misalignment.getDeltaS()));
        parameters.add(new GenericParameter<Double>(EalignVariables.DPHI.getName(), misalignment.getDeltaPhi()));
        parameters.add(new GenericParameter<Double>(EalignVariables.DTHETA.getName(), misalignment.getDeltaTheta()));
        parameters.add(new GenericParameter<Double>(EalignVariables.DPSI.getName(), misalignment.getDeltaPsi()));
        parameters
                .add(new GenericParameter<Double>(EalignVariables.MREX.getName(), misalignment.getMonitorReadErrorX()));
        parameters
                .add(new GenericParameter<Double>(EalignVariables.MREY.getName(), misalignment.getMonitorReadErrorY()));
        parameters.add(new GenericParameter<Double>(EalignVariables.MSCALX.getName(), misalignment
                .getMonitorScalingErrorX()));
        parameters.add(new GenericParameter<Double>(EalignVariables.MSCALY.getName(), misalignment
                .getMonitorScalingErrorY()));
        parameters.add(new GenericParameter<Double>(EalignVariables.AREX.getName(), misalignment.getApertureErrorX()));
        parameters.add(new GenericParameter<Double>(EalignVariables.AREY.getName(), misalignment.getApertureErrorY()));

        return parameters;
    }

}
