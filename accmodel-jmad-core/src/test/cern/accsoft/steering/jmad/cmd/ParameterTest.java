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
package cern.accsoft.steering.jmad.cmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cern.accsoft.steering.jmad.kernel.cmd.param.GenericParameter;
import cern.accsoft.steering.jmad.kernel.cmd.param.Parameter;

public class ParameterTest {
	Parameter param = null;

	@Test
	public void testIntegerParameter() {
		param = new GenericParameter<Integer>("testparam",3);
		assertEquals("testparam=3", param.compose());
		assertEquals("testparam=3", param.toString());
	}
	
	@Test
	public void testStringParameter() {
		param = new GenericParameter<String>("testparam","testvalue");
		assertEquals("testparam=testvalue", param.compose());
		assertEquals("testparam=testvalue", param.toString());
	}
	
	@Test
	public void testStringParameterQuoted() {
		param = new GenericParameter<String>("testparam","testvalue", true);
		assertEquals("testparam=\"testvalue\"", param.compose());
		assertEquals("testparam=\"testvalue\"", param.toString());
	}
	
	@Test
	public void testDoubleParameter() {
		param = new GenericParameter<Double>("testparam", 0.12345);
		assertEquals("testparam=0.12345", param.compose());
	}

}
