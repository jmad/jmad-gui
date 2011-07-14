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
package cern.accsoft.steering.jmad.util;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VarTypeTest {

	@Test
	public void testGetVarType() {
		assertEquals(MadxVarType.DOUBLE, MadxVarType.getVarType("%le"));
		assertEquals(MadxVarType.STRING, MadxVarType.getVarType("%s"));
		assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType(""));
		assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType(null));
		assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("%S"));
		assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("%LE"));
		assertEquals(MadxVarType.STRING, MadxVarType.getVarType("%23s"));
		assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("%23S"));
		assertEquals(MadxVarType.UNKNOWN, MadxVarType.getVarType("a17s"));
	}

}
