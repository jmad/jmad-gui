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
