/*
 * $Id: AskUtils.java,v 1.2 2009-03-16 16:36:33 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:36:33 $ 
 * $Revision: 1.2 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
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
