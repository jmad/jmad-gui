package cern.accsoft.steering.jmad.model;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;

public class ResultVariableTest {

	@Test
	public void testGetResultVariable() {
		assertEquals(MadxTwissVariable.BETX, MadxTwissVariable.fromMadxName("BETX"));
		assertEquals(MadxTwissVariable.BETX, MadxTwissVariable.fromMadxName("betx"));
		assertEquals(MadxTwissVariable.BETX, MadxTwissVariable.fromMadxName("BeTx"));
	}

}
