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
package cern.accsoft.steering.jmad.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cern.accsoft.steering.jmad.domain.result.tfs.TfsResultImpl;
import cern.accsoft.steering.jmad.domain.result.tfs.TfsSummary;
import cern.accsoft.steering.jmad.domain.var.enums.MadxGlobalVariable;
import cern.accsoft.steering.jmad.domain.var.enums.MadxTwissVariable;
import cern.accsoft.steering.jmad.util.LoggedTestCase;
import cern.accsoft.steering.jmad.util.MadxVarType;
import cern.accsoft.steering.jmad.util.TestFile;

public class OutputParserTest extends LoggedTestCase {
    private final static String TESTFILE_NAME = "testfile.txt";

    private TestFile testFile = new TestFile(TESTFILE_NAME);
    private TfsFileParser parser = new TfsFileParser(new File(TESTFILE_NAME));

    @Before
    public void setUp() throws TfsFileParserException {
        writeSimpleOutputFile();
        parser.parse();
    }

    @After
    public void deleteTestFile() {
        testFile.delete();
    }

    @Test(expected = TfsFileParserException.class)
    public void testParseNoFile() throws TfsFileParserException {
        testFile.delete();
        parser.parse();
    }

    @Test
    public void testParseEmptyFile() throws TfsFileParserException {
        testFile.write("");
        parser.parse();
        assertEquals("No global Parameters", 0, parser.getResult().getSummary().getKeys().size());
        assertEquals("No keys", 0, parser.getResult().getKeys().size());
    }

    @Test
    public void testReadSimpleData() {
        assertEquals("10 global Parameters", 10, parser.getResult().getSummary().getKeys().size());
        assertEquals("4 keys", 4, parser.getResult().getKeys().size());
    }

    @Test
    public void testSplitString() throws TfsFileParserException {
        String testString = "@ ORIGIN           %19s \"MAD-X 3.04.09 Linux\"\n";
        List<String> fields = TfsFileParser.splitString(testString);

        fields = TfsFileParser
                .splitString(" \"BPCK.610015\"                        0                  0 \"MONITOR\"\n");
        assertEquals("BPCK.610015", fields.get(0));
        assertEquals("0", fields.get(1));
        assertEquals("0", fields.get(2));
        assertEquals("MONITOR", fields.get(3));
    }

    @Test
    public void testGetGlobalParameter() {
        TfsSummary summary = parser.getResult().getSummary();

        assertNotNull(summary.getStringValue("TITLE"));
        assertNotNull(summary.getStringValue("ORIGIN"));
        assertNotNull(summary.getStringValue("DATE"));
        assertNotNull(summary.getStringValue("TIME"));

        assertEquals("AMDLH.610104 -", summary.getStringValue("TITLE"));
        assertEquals("MAD-X 3.04.09 Linux", summary.getStringValue("ORIGIN"));
        assertEquals("10/01/08", summary.getStringValue("DATE"));
        assertEquals("12.49.41", summary.getStringValue("TIME"));

        assertEquals(0.0, summary.getDoubleValue("orbit5"), 1e-10);
        assertEquals(53.775801, summary.getDoubleValue("GAMMATR"), 1e-10);
        assertEquals("21.700172", summary.getStringValue("Q1"));
        assertEquals("-50.397895", summary.getStringValue("dq1"));
        assertEquals(21.092760, summary.getDoubleValue(MadxGlobalVariable.Q2), 1e-10);
        assertEquals(-18.087793, summary.getDoubleValue(MadxGlobalVariable.DQ2), 1e-10);
    }

    @Test
    public void testGetKeys() {
        List<String> keys = parser.getResult().getKeys();
        // we should get the keys in correct order:
        Iterator<String> iterator = keys.iterator();
        assertEquals("NAME", iterator.next());
        assertEquals("X", iterator.next());
        assertEquals("Y", iterator.next());
        assertEquals("KEYWORD", iterator.next());
    }

    @Test
    public void testGetVarType() {
        MadxVarType varType = parser.getResult().getVarType("ABCD");
        assertNull("VarType of unknown key mus be null", varType);

        assertEquals(MadxVarType.STRING, parser.getResult().getVarType("NAME"));
        assertEquals(MadxVarType.DOUBLE, parser.getResult().getVarType("X"));
        assertEquals(MadxVarType.DOUBLE, parser.getResult().getVarType("Y"));
        assertEquals(MadxVarType.STRING, parser.getResult().getVarType("KEYWORD"));
    }

    @Test
    public void testGetStringData() {
        List<String> values = parser.getResult().getStringData(MadxTwissVariable.DDPX);
        assertNull("Values for a unknown key must be null", values);

        values = parser.getResult().getStringData(MadxTwissVariable.NAME);
        assertNotNull("Values for a known key must NOT be null", values);
        assertEquals("8 Values", 8, values.size());

        // they should be in correct order:
        Iterator<String> iterator = values.iterator();
        assertEquals("BPCK.610015", iterator.next());
        assertEquals("BPCK.610211", iterator.next());
        assertEquals("BPCK.610340", iterator.next());
        assertEquals("BPCK.610539", iterator.next());
        assertEquals("BPMIH.20204", iterator.next());
        assertEquals("BPMIH.20404", iterator.next());
        assertEquals("BPMIV.20504", iterator.next());
        assertEquals("BPMIV.20704", iterator.next());

        values = parser.getResult().getStringData(MadxTwissVariable.X);
        assertNotNull("Values for a known key must NOT be null", values);
        assertEquals("8 Values", 8, values.size());
        iterator = values.iterator();
        assertEquals("0", iterator.next());
        assertEquals("-0.001530097755", iterator.next());
        assertEquals("-0.001202659825", iterator.next());
        assertEquals("0.00151627236", iterator.next());
        assertEquals("0.002991937913", iterator.next());
        assertEquals("0.003429674605", iterator.next());
        assertEquals("0.001506437668", iterator.next());
        assertEquals("-0.0003593449524", iterator.next());

        values = parser.getResult().getStringData(MadxTwissVariable.Y);
        assertNotNull("Values for a known key must NOT be null", values);
        assertEquals("8 Values", 8, values.size());
        iterator = values.iterator();
        assertEquals("0", iterator.next());
        assertEquals("1.302602123e-16", iterator.next());
        assertEquals("5.232206825e-12", iterator.next());
        assertEquals("1.262747232e-09", iterator.next());
        assertEquals("1.854335128e-09", iterator.next());
        assertEquals("-1.062216797e-09", iterator.next());
        assertEquals("-3.936475018e-09", iterator.next());
        assertEquals("-2.494812924e-09", iterator.next());

        values = parser.getResult().getStringData(MadxTwissVariable.KEYWORD);
        assertNotNull("Values for a known key must NOT be null", values);
        assertEquals("8 Values", 8, values.size());
        iterator = values.iterator();
        assertEquals("MONITOR", iterator.next());
        assertEquals("MONITOR", iterator.next());
        assertEquals("MONITOR", iterator.next());
        assertEquals("MONITOR", iterator.next());
        assertEquals("HMONITOR", iterator.next());
        assertEquals("HMONITOR", iterator.next());
        assertEquals("VMONITOR", iterator.next());
        assertEquals("VMONITOR", iterator.next());

    }

    @Test
    public void testGetDoubleData() {
        List<Double> values = parser.getResult().getDoubleData("UNKNOWN_KEY");
        assertNull("Values for a unknown key must be null", values);

        values = parser.getResult().getDoubleData(MadxTwissVariable.NAME);
        assertNull("Values for a known key, but String type must also be null", values);

        values = parser.getResult().getDoubleData(MadxTwissVariable.KEYWORD);
        assertNull("Values for a known key, but String type must also be null", values);

        values = parser.getResult().getDoubleData(MadxTwissVariable.X);
        assertEquals("8 Values", 8, values.size());
        Iterator<Double> iterator = values.iterator();
        assertEquals(0, iterator.next(), 0.00000000001);
        assertEquals(-0.001530097755, iterator.next(), 0.00000000001);
        assertEquals(-0.001202659825, iterator.next(), 0.00000000001);
        assertEquals(0.00151627236, iterator.next(), 0.00000000001);
        assertEquals(0.002991937913, iterator.next(), 0.00000000001);
        assertEquals(0.003429674605, iterator.next(), 0.00000000001);
        assertEquals(0.001506437668, iterator.next(), 0.00000000001);
        assertEquals(-0.0003593449524, iterator.next(), 0.00000000001);

        values = parser.getResult().getDoubleData(MadxTwissVariable.Y);
        assertEquals("8 Values", 8, values.size());
        iterator = values.iterator();
        assertEquals(0, iterator.next(), 0.00000000001);
        assertEquals(1.302602123e-16, iterator.next(), 0.00000000001);
        assertEquals(5.232206825e-12, iterator.next(), 0.00000000001);
        assertEquals(1.262747232e-09, iterator.next(), 0.00000000001);
        assertEquals(1.854335128e-09, iterator.next(), 0.00000000001);
        assertEquals(-1.062216797e-09, iterator.next(), 0.00000000001);
        assertEquals(-3.936475018e-09, iterator.next(), 0.00000000001);
        assertEquals(-2.494812924e-09, iterator.next(), 0.00000000001);

    }

    @Test
    public void testGetElementIndex() {
        TfsResultImpl result = parser.getResult();

        assertEquals(0, (int) result.getElementIndex("BPCK.610015"));
        assertEquals(4, (int) result.getElementIndex("BPMIH.20204"));
        assertEquals(7, (int) result.getElementIndex("BPMIV.20704"));

        assertNull("Nonexistent element.", result.getElementIndex("UVWM.23332"));

    }

    private void writeSimpleOutputFile() {
        testFile.write("@ TITLE            %14s \"AMDLH.610104 -\"\n"
                + "@ ORIGIN           %19s \"MAD-X 3.04.09 Linux\"\n" + "@ DATE             %08s \"10/01/08\"\n"
                + "@ TIME             %08s \"12.49.41\"\n" + "@ ORBIT5           %le      0.000000\n"
                + "@ GAMMATR          %le     53.775801\n" + "@ Q1               %le     21.700172\n"
                + "@ Q2               %le     21.092760\n" + "@ DQ1              %le    -50.397895\n"
                + "@ DQ2              %le    -18.087793\n"
                + "* NAME                                X                  Y KEYWORD\n"
                + "$ %s                                %le                %le %s      \n"
                + " \"BPCK.610015\"                        0                  0 \"MONITOR\"\n"
                + " \"BPCK.610211\"          -0.001530097755    1.302602123e-16 \"MONITOR\"\n"
                + " \"BPCK.610340\"          -0.001202659825    5.232206825e-12 \"MONITOR\"\n"
                + " \"BPCK.610539\"            0.00151627236    1.262747232e-09 \"MONITOR\"\n"
                + " \"BPMIH.20204\"           0.002991937913    1.854335128e-09 \"HMONITOR\"\n"
                + " \"BPMIH.20404\"           0.003429674605   -1.062216797e-09 \"HMONITOR\"\n"
                + " \"BPMIV.20504\"           0.001506437668   -3.936475018e-09 \"VMONITOR\"\n"
                + " \"BPMIV.20704\"         -0.0003593449524   -2.494812924e-09 \"VMONITOR\"\n");
    }
}
