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
package cern.accsoft.steering.util.gui;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * Some util-methods for simple user interaction.
 * 
 * @author kfuchsbe
 *
 */
public class AskUtils {
	private final static Logger logger = Logger.getLogger(AskUtils.class); 
	
	/**
	 * private constructor, no instantiation
	 */
	private AskUtils() {
		// only static methods
	}
	
	/**
	 * a rather simple method to ask the user for a double value
	 * 
	 * @param message
	 * @param initValue
	 * @return
	 */
	public final static Double askDouble(String message, Double initValue) {
		String input = JOptionPane.showInputDialog(message, initValue);
		try {
			Double doubleValue = Double.parseDouble(input);
			return doubleValue;
		} catch (NumberFormatException e) {
			logger.warn("Could not convert user input '" + input + "' to a double value!");
			return null;
		}
	}
	
	
}
