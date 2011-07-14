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
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SetEqual extends AbstractJMadExecutable implements Command {
    private final String valueName;
    private final Double value;

    public SetEqual(String valueName, Double value) {
        this.valueName = valueName;
        this.value = value;
    }

    @Override
    public String compose() {
        return valueName + " = " + value + ";";
    }

    @Override
    public String getName() {
        return this.valueName;
    }

    @Override
    public List<Parameter> getParameters() {
        /*
         * XXX check if really necessary, that this implements a command
         */
        List<Parameter> retVal = new ArrayList<Parameter>();
        retVal.add(new GenericParameter<Double>(this.valueName, this.value));
        return retVal;
    }
}
