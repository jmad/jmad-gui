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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cern.accsoft.steering.jmad.domain.knob.strength.Strength;
import cern.accsoft.steering.jmad.io.StrengthFileParser;
import cern.accsoft.steering.jmad.io.StrengthFileParserException;

public class StrengthFileParserTest {
	private TestFile testFile = new TestFile("test.str");
	private StrengthFileParser parser = null;

	@Before
	public void setUp() throws Exception {
		testFile.delete();
		parser = new StrengthFileParser(testFile.getFile());
	}

	@After
	public void tearDown() throws Exception {
		testFile.delete();
	}

	@Test
	public void testParseCorrectFile() throws StrengthFileParserException {
		testFile.write("! This is a comment \n" + "! another comment      \n"
				+ " // yet another comment \n" + "\n\n"
				+ "abiv.610013          :=  0.000000000000000 ;\n"
				+ "abih.610104          :=  0.000743880000000 ;");

		parser.parse();

		List<Strength> initValues = parser.getStrengths();

		assertEquals("We should get 2 values", 2, initValues.size());
		assertEquals("abiv.610013", initValues.get(0).getName());
		assertEquals(0.000000000000000, initValues.get(0).getValue(), 0.0);

		assertEquals("abih.610104", initValues.get(1).getName());
		assertEquals(0.000743880000000, initValues.get(1).getValue(), 0.0);

	}

	@Test
	public void testParseSimpleEquals() throws StrengthFileParserException {
		testFile.write("abiv.610013          = 0.000000000000000;\n"
				+ "abih.610104=0.000743880000000;");

		parser.parse();

		List<Strength> initValues = parser.getStrengths();

		assertEquals("We should get 2 values", 2, initValues.size());
		assertEquals("abiv.610013", initValues.get(0).getName());
		assertEquals(0.000000000000000, initValues.get(0).getValue(), 0.0);

		assertEquals("abih.610104", initValues.get(1).getName());
		assertEquals(0.000743880000000, initValues.get(1).getValue(), 0.0);
	}

	@Test
	public void testParseEndlineComments() throws StrengthFileParserException {
		testFile
				.write("abiv.610013          :=  0.000000000000000 ; // endline comment A \n"
						+ "abih.610104          :=  0.000743880000000 ; ! endline comment B  \n "
						+ "abih.610206          :=  0.000755840000000 ; // commentC ! this will be ignored \n");

		parser.parse();

		List<Strength> initValues = parser.getStrengths();

		assertEquals("We should get 3 values", 3, initValues.size());
		assertEquals("abiv.610013", initValues.get(0).getName());
		assertEquals(0.000000000000000, initValues.get(0).getValue(), 0.0);
		assertEquals("endline comment A", initValues.get(0).getDescription());

		assertEquals("abih.610104", initValues.get(1).getName());
		assertEquals(0.000743880000000, initValues.get(1).getValue(), 0.0);
		assertEquals("endline comment B", initValues.get(1).getDescription());

		assertEquals("abih.610206", initValues.get(2).getName());
		assertEquals(0.000755840000000, initValues.get(2).getValue(), 0.0);
		assertEquals("commentC", initValues.get(2).getDescription());

	}

	@Test
	public void testParseNoEquals() throws StrengthFileParserException {
		testFile
				.write("abiv.610013          :  0.000000000000000 ; // endline comment A \n");
		parser.parse();
		List<Strength> strengths = parser.getStrengths();
		assertEquals("There should be no lines which return a valid strength.",
				0, strengths.size());
	}

	@Test(expected = StrengthFileParserException.class)
	public void testParseNoSemicolon() throws StrengthFileParserException {
		testFile
				.write("abiv.610013          :=  0.000000000000000  // endline comment A \n");
		parser.parse();
	}

	@Test
	public void testParseTextAfterReturn() throws StrengthFileParserException {
		testFile
				.write("abiv.610013          :=  0.000000000000000 ; // endline comment A \n"
						+ " \n\n return; \n"
						+ "abih.610104          :=  0.000743880000000 ; ! endline comment B  \n "
						+ "abih.610206          :=  0.000755840000000 ; // commentC ! this will be ignored \n");

		parser.parse();

		List<Strength> initValues = parser.getStrengths();

		assertEquals("We should get only 1 value!", 1, initValues.size());
		assertEquals("abiv.610013", initValues.get(0).getName());
		assertEquals(0.000000000000000, initValues.get(0).getValue(), 0.0);
		assertEquals("endline comment A", initValues.get(0).getDescription());

	}
}
