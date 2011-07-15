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

package cern.accsoft.steering.jmad.domain.twiss;

import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TwissInitialConditionsConverter implements Converter {

    /** The name off the attribute value */
    private static final String ATTR_NAME_VALUE = "value";

    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext ctx) {

        TwissInitialConditionsImpl twiss = (TwissInitialConditionsImpl) object;
        writer.addAttribute("name", twiss.getName());

        writer.startNode("chrom");
        writer.addAttribute(ATTR_NAME_VALUE, String.valueOf(twiss.isCalcChromaticFunctions()));
        writer.endNode();

        writer.startNode("closed-orbit");
        writer.addAttribute(ATTR_NAME_VALUE, String.valueOf(twiss.isClosedOrbit()));
        writer.endNode();

        writer.startNode("centre");
        writer.addAttribute(ATTR_NAME_VALUE, String.valueOf(twiss.isCalcAtCenter()));
        writer.endNode();

        for (MadxTwissVariable variable : twiss.getMadxVariables()) {
            Double value = twiss.getValue(variable);
            if (value != null) {
                writer.startNode(variable.getMadxName().toLowerCase());
                writer.addAttribute(ATTR_NAME_VALUE, value.toString());
                writer.endNode();
            }
        }

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {

        String name = reader.getAttribute("name");
        TwissInitialConditionsImpl retVal = new TwissInitialConditionsImpl(name);

        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            if ("chrom".equals(nodeName)) {
                retVal.setCalcChromaticFunctions(Boolean.getBoolean(reader.getAttribute(ATTR_NAME_VALUE)));
            } else if ("closed-orbit".equals(nodeName)) {
                retVal.setClosedOrbit(Boolean.getBoolean(reader.getAttribute(ATTR_NAME_VALUE)));
            } else if ("centre".equals(nodeName)) {
                retVal.setCalcAtCenter(Boolean.getBoolean(reader.getAttribute(ATTR_NAME_VALUE)));
            } else {
                MadxTwissVariable twissVariable = MadxTwissVariable.fromMadxName(nodeName);
                if (retVal.getMadxVariables().contains(twissVariable)) {
                    retVal.setValue(twissVariable, Double.valueOf(reader.getAttribute(ATTR_NAME_VALUE)));
                }
            }
            reader.moveUp();
        }
        return retVal;
    }

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
        return clazz.equals(TwissInitialConditionsImpl.class);
    }
}
