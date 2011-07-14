// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, Kajetan Fuchsberger. All rights reserved.
 * 
 * JMad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JMad.  If not, see <http://www.gnu.org/licenses/>.
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
import cern.accsoft.steering.jmad.domain.misalign.MisalignmentConfiguration;
import cern.accsoft.steering.jmad.kernel.cmd.Command;
import cern.accsoft.steering.jmad.kernel.cmd.EalignCommand;
import cern.accsoft.steering.jmad.kernel.cmd.SelectCommand;

/**
 * This class represents a task, which applies a certain misalignment to a element.
 * 
 * @author kfuchsbe
 */
public class SetMisalignment extends AbstractTask {

    /** the flag to use for the select command to set the misalignment */
    private static final String SELECT_FLAG_ERROR = "error";

    /** the machine-element to which to apply the misalignment */
    private final String elementName;

    /** contains the misalignment values, which to apply to the element */
    private final Misalignment misalignment;

    /**
     * the constructor. Needs the element and the misalignment to apply.
     * 
     * @param config the misalignment-config to apply
     */
    public SetMisalignment(MisalignmentConfiguration config) {
        this.elementName = config.getElementName();
        this.misalignment = config.getMisalignment();
    }

    @Override
    protected List<Command> getCommands() {
        List<Command> commands = new ArrayList<Command>();

        if ((this.elementName != null) && (this.misalignment != null)) {
            /* first reset the select-command */
            SelectCommand select = new SelectCommand();
            select.setFlag(SELECT_FLAG_ERROR);
            select.setClear(true);
            commands.add(select);

            /* set the select-command for one element */
            select = new SelectCommand();
            select.setFlag(SELECT_FLAG_ERROR);
            select.setPattern(this.elementName);
            commands.add(select);

            /* set the misalignment for the element */
            EalignCommand ealign = new EalignCommand(this.misalignment);
            commands.add(ealign);
        }
        return commands;
    }

}
