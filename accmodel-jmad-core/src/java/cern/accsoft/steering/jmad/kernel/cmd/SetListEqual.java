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
package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class SetListEqual extends AbstractJMadExecutable implements Command {
    private final Map<String, Double> valuePairs;

    public SetListEqual(Map<String, Double> valuePairs) {
        this.valuePairs = valuePairs;
    }

    @Override
    public String compose() {
        StringBuffer cmd = new StringBuffer("");
        for (String valueName : this.valuePairs.keySet()) {
            cmd.append(valueName + " = " + this.valuePairs.get(valueName) + ";\n");
        }
        return cmd.toString();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public List<Parameter> getParameters() {
        return new ArrayList<Parameter>(0);
    }
}
