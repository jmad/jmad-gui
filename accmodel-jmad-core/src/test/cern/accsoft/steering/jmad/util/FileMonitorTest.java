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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import cern.accsoft.steering.jmad.util.FileMonitor.WaitingFailedException;

public class FileMonitorTest extends LoggedTestCase {
    private FileMonitor fileMonitor = null;
    private TestFile testFile = null;

    private final static String TEST_FILE_NAME = "testfile.txt";
    private final static long WAIT_MILLISEC = 200;

    @Before
    public void createFileMonitor() {
        fileMonitor = new FileMonitor(new File(TEST_FILE_NAME));
    }

    @Test
    public void testNoFile() throws WaitingFailedException {
        boolean fileExists = fileMonitor.waitForFile(WAIT_MILLISEC);
        assertFalse("filemonitor should time out, because there exists no file.", fileExists);
    }

    @Test
    public void testFileExistsAtStart() throws WaitingFailedException {
        testFile = new TestFile(TEST_FILE_NAME);
        testFile.write("");
        boolean fileExists = fileMonitor.waitForFile(WAIT_MILLISEC);
        assertTrue("No timeout, since file exists.", fileExists);
    }

}
