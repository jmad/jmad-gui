/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.util;

import static cern.accsoft.steering.jmad.util.FileUtil.tail;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

public class FileUtilTest {

    private TestFile testFile = new TestFile("test-file.txt");

    @After
    public void after() {
        testFile.delete();
    }

    @Test
    public void tailWorksWithSmallerRequestThanFile() {
        testFile.write(lineString(10));
        assertEquals(Arrays.asList("line 7", "line 8", "line 9"), tail(testFile.getFile(), 3));
    }

    @Test
    public void tailWorksWithSameSizeAsRequested() {
        testFile.write(lineString(3));
        assertEquals(Arrays.asList("line 0", "line 1", "line 2"), tail(testFile.getFile(), 3));
    }
    
    @Test
    public void tailWorksWithEmptyFile() {
        testFile.write("");
        assertEquals(emptyList(), tail(testFile.getFile(), 3));
    }
    
    @Test
    public void tailWorksWithMoreRequested() {
        testFile.write(lineString(3));
        assertEquals(Arrays.asList("line 0", "line 1", "line 2"), tail(testFile.getFile(), 3));
    }

    private String lineString(int numberOfLines) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfLines; i++) {
            builder.append("line " + i + "\n");
        }
        builder.delete(builder.length() - 1, builder.length());
        String string = builder.toString();
        return string;
    }
}
