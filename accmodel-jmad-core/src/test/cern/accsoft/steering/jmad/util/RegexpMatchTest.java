package cern.accsoft.steering.jmad.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegexpMatchTest {
	private final static String REGEX = "(?i)^BPM.*B2\\.H";
	
	@Test
	public void testRegexpMatch() {
		assertTrue("BPMSX.4L8.B2.H".matches(REGEX));
	}
}
