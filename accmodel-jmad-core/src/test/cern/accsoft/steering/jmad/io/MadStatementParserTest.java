/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import cern.accsoft.steering.jmad.util.TestFile;

public class MadStatementParserTest {

    private final static String TESTFILE_NAME = "testfile.txt";
    private TestFile testFile = new TestFile(TESTFILE_NAME);

    @After
    public void cleanUp() {
        testFile.delete();
    }

    @Test
    public void onlyCommentsResultInEmptyList() throws IOException {
        prepare("//");
        assertEquals(Collections.emptyList(), statements());
    }

    @Test
    public void newlinesGiveEmptyList() throws IOException {
        prepare("\n//\n");
        assertEquals(Collections.emptyList(), statements());
    }

    @Ignore
    @Test
    public void parseValidMultilineFile() throws IOException {
        prepareValidFile();
        assertEquals(list("a=1;", "a=2;", "b=2;", "a=3;", "a=4;", "a=4;", "a=6;", "a=9;"), statements());
    }

    private List<String> list(String... strings) {
        return Arrays.asList(strings);
    }

    public void prepareValidFile() {
        prepare("a=1;\n" //
                + "a=2;b=2;\n" //
                + "a=3;//b=2;\n" //
                + "a=4;!b=2;\n" //
                + "!a=4;!b=2;\n" //
                + "a=4;/*a=5;*/a=6;\n" //
                + "/*.........\n" //
                + "*/\n" //
                + "a\n" //
                + "=\n" //
                + "9;\n");
    }

    private List<String> statements() throws IOException {
        return MadStatementParser.parse(testFile.getFile());
    }

    private void prepare(String text) {
        testFile.write(text);
    }

}
