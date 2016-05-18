// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    @Test
    public void testWaitUntilReady() throws JMadException {
        kernel.start();
        kernel.writeCommand("System \"echo > " + file.getAbsolutePath() + "\";");
        kernel.waitUntilReady();
        assertTrue("madx should have created a file.", file.exists());
    }

    @Test(expected = WaitForMadxTimedOutException.class)
    public void testWaitUntilReadyTimeout() throws JMadException {
        kernel.start();
        kernel.setTimeout((long) 0);
        kernel.writeCommand("System \"echo > " + file.getAbsolutePath() + "\";");
        kernel.waitUntilReady();
    }
}
