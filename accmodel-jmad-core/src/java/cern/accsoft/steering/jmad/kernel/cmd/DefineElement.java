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

package cern.accsoft.steering.jmad.kernel.cmd;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.kernel.AbstractJMadExecutable;
import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class DefineElement extends AbstractJMadExecutable implements Command {

    private Element element;

    public DefineElement(Element element) {
        this.element = element;
    }

    @Override
    public String compose() {
        String retVal = this.getName() + ": " + this.element.getMadxElementType().getMadxName();
        for (Parameter parameter : this.getParameters()) {
            retVal += ", " + parameter.compose();
        }
        retVal += ";";
        return retVal;
    }

    @Override
    public String getName() {
        return this.element.getName();
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        for (String attribute : this.element.getAttributeNames()) {
            if (this.element.getAttribute(attribute) != null) {
                parameters.add(new GenericParameter<Double>(attribute, this.element.getAttribute(attribute)));
            }
        }
        return parameters;
    }

}
