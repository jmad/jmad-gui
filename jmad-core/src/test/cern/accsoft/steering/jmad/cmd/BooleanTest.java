package cern.accsoft.steering.jmad.cmd;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BooleanTest {

	@Test
	public void testParseBoolean() {
		assertFalse(Boolean.parseBoolean("abc"));
		assertFalse(Boolean.parseBoolean(null));
		assertTrue(Boolean.parseBoolean("true"));
	}

}
