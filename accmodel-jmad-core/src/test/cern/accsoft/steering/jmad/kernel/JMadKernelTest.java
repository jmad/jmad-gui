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
package cern.accsoft.steering.jmad.kernel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import cern.accsoft.steering.jmad.JMadException;
import cern.accsoft.steering.jmad.bin.MadxBinImpl;
import cern.accsoft.steering.jmad.util.JMadPreferences;
import cern.accsoft.steering.jmad.util.JMadPreferencesImpl;
import cern.accsoft.steering.jmad.util.TempFileUtilImpl;

public class JMadKernelTest extends cern.accsoft.steering.jmad.util.LoggedTestCase {

    File file = new File("madx-test.out");
    JMadKernelImpl kernel = new JMadKernelImpl();
    {
        JMadPreferences prefs = new JMadPreferencesImpl();
        TempFileUtilImpl fileUtil = new TempFileUtilImpl();
        fileUtil.setPreferences(prefs);
        fileUtil.init();

        MadxBinImpl madxBin = new MadxBinImpl();
        madxBin.setFileUtil(fileUtil);
        madxBin.init();

        kernel.setFileUtil(fileUtil);
        kernel.setMadxBin(madxBin);
        kernel.setPreferences(prefs);
    }

    @After
    public void deleteTestFile() throws JMadException {
        if (!file.delete()) {
            ; // ignore, may not exist.
        }

        if (kernel.isMadxRunning()) {
            kernel.stop();
        }
    }

    // @Ignore("Takes to long")
    @Test
    public void testEmptyCommands() throws JMadException {
        kernel.start();
        int exitValue = kernel.stop();

        assertEquals("Call of MadX with empty file should return correctly.", 0, exitValue);
    }

    // @Ignore("Takes to long")
    @Test
    public void testWaitUntilReady() throws JMadException {
        kernel.start();
        kernel.writeCommand("System \"echo > " + file.getAbsolutePath() + "\";");
        kernel.waitUntilReady();
        assertTrue("madx should have created a file.", file.exists());
    }

    // @Ignore("Takes to long")
    @Test
    public void testWaitUntilReadyTimeout() throws JMadException {
        kernel.start();
        kernel.setTimeout((long) 0);
        kernel.writeCommand("System \"echo > " + file.getAbsolutePath() + "\";");
        boolean timedout = kernel.waitUntilReady();
        assertTrue("madx should not have been that fast in creating the file.", timedout);
    }
}
