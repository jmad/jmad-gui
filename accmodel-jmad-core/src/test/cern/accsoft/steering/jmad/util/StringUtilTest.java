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

import static cern.accsoft.steering.jmad.util.StringUtil.join;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringUtilTest {

    private static final List<Object> ANY_COLLECTION = Collections.emptyList();
    private static final String ANY_STRING = "any string";
    private static final String ANY_SEPARATOR = "|ANY SEPARATOR|";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testIsWhiteSpace() {
        assertTrue(StringUtil.isWhitespacesOnly(""));
        assertTrue(StringUtil.isWhitespacesOnly(" "));
        assertTrue(StringUtil.isWhitespacesOnly(" \n "));
        assertTrue(StringUtil.isWhitespacesOnly("\r\n"));

        assertFalse(StringUtil.isWhitespacesOnly("a"));
        assertFalse(StringUtil.isWhitespacesOnly("1"));
    }

    @Test
    public void nullListThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Collection of objects");
        join(null, ANY_SEPARATOR);
    }

    @Test
    public void nullSeparatorThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Separator");
        join(ANY_COLLECTION, null);
    }

    @Test
    public void joinWorksForEmptyList() {
        assertEquals("", join(Collections.emptyList(), ANY_SEPARATOR));
    }

    @Test
    public void joinOneEmptyStringResultsInEmptyString() {
        assertEquals("", join(Arrays.asList(""), ANY_SEPARATOR));
    }

    @Test
    public void joinTwoEmptyStringsResultsInSeparator() {
        assertEquals(ANY_SEPARATOR, join(Arrays.asList("", ""), ANY_SEPARATOR));
    }

    @Test
    public void joinThreeeNonEmptyStringsIsCorrect() {
        assertEquals(ANY_STRING + ANY_SEPARATOR + ANY_STRING + ANY_SEPARATOR + ANY_STRING,
                join(Arrays.asList(ANY_STRING, ANY_STRING, ANY_STRING), ANY_SEPARATOR));
    }

}
