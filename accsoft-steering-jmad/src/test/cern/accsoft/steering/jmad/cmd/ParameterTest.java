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
