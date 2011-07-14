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
	public void marshal(Object object, HierarchicalStreamWriter writer,
			MarshallingContext ctx) {

		TwissInitialConditionsImpl twiss = (TwissInitialConditionsImpl) object;
		writer.addAttribute("name", twiss.getName());

		writer.startNode("chrom");
		writer.addAttribute(ATTR_NAME_VALUE, String.valueOf(twiss
				.isCalcChromaticFunctions()));
		writer.endNode();

		writer.startNode("closed-orbit");
		writer.addAttribute(ATTR_NAME_VALUE, String.valueOf(twiss
				.isClosedOrbit()));
		writer.endNode();

		writer.startNode("centre");
		writer.addAttribute(ATTR_NAME_VALUE, String.valueOf(twiss
				.isCalcAtCenter()));
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
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext ctx) {

		String name = reader.getAttribute("name");
		TwissInitialConditionsImpl retVal = new TwissInitialConditionsImpl(name);

		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String nodeName = reader.getNodeName();
			if ("chrom".equals(nodeName)) {
				retVal.setCalcChromaticFunctions(Boolean.getBoolean(reader
						.getAttribute(ATTR_NAME_VALUE)));
			} else if ("closed-orbit".equals(nodeName)) {
				retVal.setClosedOrbit(Boolean.getBoolean(reader
						.getAttribute(ATTR_NAME_VALUE)));
			} else if ("centre".equals(nodeName)) {
				retVal.setCalcAtCenter(Boolean.getBoolean(reader
						.getAttribute(ATTR_NAME_VALUE)));
			} else {
				MadxTwissVariable twissVariable = MadxTwissVariable
						.fromMadxName(nodeName);
				if (retVal.getMadxVariables().contains(twissVariable)) {
					retVal.setValue(twissVariable, Double.valueOf(reader
							.getAttribute(ATTR_NAME_VALUE)));
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
