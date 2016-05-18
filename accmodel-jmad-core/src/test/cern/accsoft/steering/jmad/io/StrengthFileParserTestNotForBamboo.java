/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package cern.accsoft.steering.jmad.io;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class StrengthFileParserTestNotForBamboo {
    private static final String TEST_FILE_2_NAME_FOR_PARSER = "testFileForParser/testFile.str";
    private static final File DENSE_FILE_TO_PARSE = new File(TEST_FILE_2_NAME_FOR_PARSER);
    private StrengthFileParser parser;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        parser = new StrengthFileParser(DENSE_FILE_TO_PARSE);
    }

    @Test
    public void testDenseFiles() throws StrengthFileParserException {
        parser.parse();
    }
}
