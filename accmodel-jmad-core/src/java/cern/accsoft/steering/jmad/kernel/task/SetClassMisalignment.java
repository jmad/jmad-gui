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
 * $Id: SetMisalignment.java,v 1.2 2009-03-16 16:35:33 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:35:33 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.kernel.task;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.misalign.Misalignment;
import cern.accsoft.steering.jmad.domain.misalign.PatternOrClassMisalignmentConfiguration;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.EOptionCommand;
import cern.accsoft.steering.jmad.kernel.cmd.GaussEalignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.SelectCommand;

/**
 * This class represents a task, which applies a certain misalignment to a class/patterns of elements with defined
 * random gaussian distribution for DX and DY.
 * 
 * @author agorzaws
 */
public class SetClassMisalignment extends AbstractTask {

    /** the flag to use for the select command to set the misalignment */
    private static final String SELECT_FLAG_ERROR = "error";

    /** contains the misalignment values, which to apply to the element */
    private final Misalignment misalignment;

    private final String elementsClass;

    private final String pattern;

    private final double gaussianDistribution;

    private final Double seed;

    /**
     * the constructor. Needs the element and the misalignment to apply.
     * 
     * @param config the misalignment-config to apply
     */
    public SetClassMisalignment(PatternOrClassMisalignmentConfiguration config) {
        this.elementsClass = config.getElementClass();
        this.pattern = config.getElementName();
        this.misalignment = config.getMisalignment();
        this.gaussianDistribution = config.getGaussianDistribution();
        this.seed = Double.valueOf(config.getSeed());
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();

        if ((this.elementsClass != null) && (this.misalignment != null)) {

            EOptionCommand eOptionCommand = new EOptionCommand(seed, false);
            commands.add(eOptionCommand);

            /* first reset the select-command */
            SelectCommand select = new SelectCommand();
            select.setFlag(SELECT_FLAG_ERROR);
            select.setClear(true);
            commands.add(select);

            /* set the select-command for one element */
            select = new SelectCommand();
            select.setFlag(SELECT_FLAG_ERROR);
            if (isValueSet(elementsClass))
                select.setElementClass(this.elementsClass);
            if (isValueSet(pattern)) {
                select.setPattern(this.pattern);
            }
            commands.add(select);

            /* set the misalignment for the element class */
            GaussEalignCommand ealign = new GaussEalignCommand(this.misalignment, gaussianDistribution);
            commands.add(ealign);
        }
        return commands;
    }

    private boolean isValueSet(String elementToTest) {
        return elementToTest != null && elementToTest != "";
    }

}
