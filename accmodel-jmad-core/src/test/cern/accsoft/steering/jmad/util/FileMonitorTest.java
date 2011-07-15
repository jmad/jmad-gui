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
