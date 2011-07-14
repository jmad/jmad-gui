// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
 * $Id: EOptionCommand.java,v 1.2 2009-01-27 10:17:58 kfuchsbe Exp $
 * 
 * $Date: 2009-01-27 10:17:58 $ $Revision: 1.2 $ $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

/**
 * the madx-command to change error options: EOPTION,SEED=real,ADD=logical;
 * 
 * @author kfuchsbe
 */
public class EOptionCommand extends AbstractCommand {

    /** the name of the command */
    private static final String CMD_NAME = "eoption";
    /**
     * SEED: Selects a particular sequence of random values. A SEED value is an integer in the range [0...999999999]
     * (default: 123456789). SEED alone continues with the current sequence.
     */
    private final Double seed;

    /**
     * ADD: If this logical flag is set, an EALIGN or EFCOMP, causes the errors to be added on top of existing ones. If
     * it is not set, new errors overwrite any previous definitions. The default values is TRUE.
     */
    private final Boolean add;

    /**
     * the constructor, which sets both fields
     * 
     * @param seed the new seed value for MadX random generation.
     * @param add if set to if <code>true</code> the errors defined in the following will add up, if <code>false</code>
     *            they will not.
     * @see #seed
     * @see #add
     */
    public EOptionCommand(Double seed, Boolean add) {
        this.seed = seed;
        this.add = add;
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public List<Parameter> getParameters() {
        ArrayList<Parameter> parameters = new ArrayList<Parameter>();

        parameters.add(new GenericParameter<Double>("seed", seed));

        /*
         * this is kind of special boolean parameter: it does not follow the convention, that it is omitted, when it is
         * meant to be false! It has to be set explicitly to false. We therefore use a string-parameter.
         */
        if (add != null) {
            parameters.add(new GenericParameter<String>("add", add.toString()));
        }
        return parameters;
    }

}
