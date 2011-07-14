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
 * $Id: SimpleTwissDemo.java,v 1.3 2009-03-16 16:35:33 kfuchsbe Exp $
 * 
 * $Date: 2009-03-16 16:35:33 $ 
 * $Revision: 1.3 $ 
 * $Author: kfuchsbe $
 * 
 * Copyright CERN, All Rights Reserved.
 */
package cern.accsoft.steering.jmad.demo;

import java.util.List;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.optics.Optic;
import cern.accsoft.steering.jmad.domain.optics.OpticPoint;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.model.JMadModel;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;
import cern.accsoft.steering.jmad.service.JMadService;
import cern.accsoft.steering.jmad.service.JMadServiceFactory;

/**
 * this demo shows, how to initialize a model and output the some-parameters
 * 
 * @author kfuchsbe
 * 
 */
public class SimpleTwissDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/* define here, how many values to print */
		int maxCount = 10;

		/*
		 * uncomment the following line, if you want to see debug messages
		 */
		// org.apache.log4j.BasicConfigurator.configure();
		System.out.print("\n\nSimple twiss demo.\n");
		System.out.print("==================\n\n");

		/*
		 * first we have to create a new JMad service.
		 */
		JMadService jmadService = JMadServiceFactory.createJMadService();

		/*
		 * then find a model definition
		 */
		JMadModelDefinition modelDefinition = jmadService
				.getModelDefinitionManager().getModelDefinition("example",
						false);

		/* create the model and initialize it */
		System.out.print("initializing model " + modelDefinition.toString()
				+ "...\n");
		JMadModel model = jmadService.createModel(modelDefinition);

		try {
			model.init();

			/*
			 * the twiss-parameters are quite easy to find:
			 */
			System.out.print("\n\nSome optics values:\n");
			System.out.print("-------------------\n\n");

			Optic optics = model.getOptics();
			int count = 0;
			for (OpticPoint point : optics.getAllPoints()) {
				System.out.print(point.getName() + ": phaseX=" + point.getMux()
						+ "; betaX=" + point.getBetx() + ";\n");
				count++;
				if (count > maxCount) {
					break;
				}
			}

			/*
			 * If we want to get something else we define, which stuff we want
			 * to see ... as an example we print the X and Y on some BPMS:
			 */
			System.out.print("\n\nSome other values:\n");
			System.out.print("------------------\n\n");

			TfsResultRequestImpl request = new TfsResultRequestImpl();
			/* a regexp for the elements we want to retrieve */
			request.addElementFilter("BPM.*");
			/* and the variables, which we want to see */
			request.addVariable(MadxTwissVariable.NAME);
			request.addVariable(MadxTwissVariable.X);
			request.addVariable(MadxTwissVariable.Y);

			/* run the twiss and print the results */
			TfsResult result = model.twiss(request);

			List<String> elementNames = result
					.getStringData(MadxTwissVariable.NAME);
			List<Double> xValues = result.getDoubleData(MadxTwissVariable.X);
			List<Double> yValues = result.getDoubleData(MadxTwissVariable.Y);

			count = 0;
			for (String name : elementNames) {
				int index = result.getElementIndex(name);
				System.out.print(name + ": X=" + xValues.get(index) + "; Y="
						+ yValues.get(index) + ";\n");
				count++;
				if (count > maxCount) {
					break;
				}
			}

			/* finally we cleanup and close the madx-kernel */
			model.cleanup();
		} catch (JMadModelException e) {
			e.printStackTrace();
		} finally {
		}

	}
}
