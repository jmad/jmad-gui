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
package cern.accsoft.steering.jmad.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cern.accsoft.steering.jmad.JMadTestCase;
import cern.accsoft.steering.jmad.domain.ex.JMadModelException;
import cern.accsoft.steering.jmad.domain.machine.RangeDefinitionImpl;
import cern.accsoft.steering.jmad.domain.machine.SequenceDefinition;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResult;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultRequestImpl;
import cern.accsoft.steering.jmad.domain.twiss.TwissInitialConditionsImpl;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.modeldefs.domain.JMadModelDefinition;

public class RotateSequenceTest extends JMadTestCase {
	private static JMadModelDefinition modelDefinition;
	private static JMadModel model;

	@BeforeClass
	public static void classSetUp() {
		modelDefinition = JMadTestCase.findExampleModelDefinition();
		model = getJMadService().createModel(modelDefinition);
	}

	@Before
	public void setUp() throws Exception {
		model.init();
	}

	@After
	public void tearDown() throws Exception {
		model.cleanup();
	}

	@Test
	public void testSetRotatedRange() throws JMadModelException {

		TfsResult result = model.twiss(new TfsResultRequestImpl());
		assertEquals("TI2$START", result.getStringData(MadxTwissVariable.NAME)
				.get(0));

		
		model.getOptics().getPointByName("BPMIV.20704");
		
		
		SequenceDefinition sequenceDefinition = modelDefinition
				.getDefaultSequenceDefinition();
		TwissInitialConditionsImpl initialConditions = new TwissInitialConditionsImpl();
		initialConditions.setBetx(18.27133201);
		initialConditions.setBety(99.55875346);
		initialConditions.setDx(-0.7535789232);
		initialConditions.setDy(2.378652931);
		initialConditions.setAlfx(-0.4889547729);
		initialConditions.setAlfy(2.38571288);
		initialConditions.setDpx(-0.06017393071);
		initialConditions.setDpy(-0.07387121153);

		RangeDefinitionImpl rangeDefinition = new RangeDefinitionImpl(
				sequenceDefinition, "ALL rotated", initialConditions);
		rangeDefinition.setStartElementName("BPMIV.20704");
		model.setActiveRangeDefinition(rangeDefinition);

		result = model.twiss(new TfsResultRequestImpl());
		assertEquals("BPMIV.20704", result
				.getStringData(MadxTwissVariable.NAME).get(0));
	}
}
