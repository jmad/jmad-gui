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
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * represents the command for the misaligning one or more elements. The elements have to be selected before by the
 * select command. EALIGN, DX=real,DY=real,DS=real, DPHI=real,DTHETA=real,DPSI=real, MREX=real,MREY=real,
 * MSCALX=real,MSCALY=real, AREX=real,AREY=real;
 * 
 * @author kfuchsbe
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
        parameters.add(new GenericParameter<Double>("dx", misalignment.getDeltaX()));
        parameters.add(new GenericParameter<Double>("dy", misalignment.getDeltaY()));
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