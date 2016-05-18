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

/**
 * 
 */
package cern.accsoft.steering.jmad.util;

import static java.util.Objects.requireNonNull;

import java.util.Collection;

/**
 * some utils to handle strings
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public final class StringUtil {

    private StringUtil() {
        /* only static methods */
    }

    /**
     * checks if the string consists only of whitespaces. According to PMD this is a more efficient method than checking
     * string.trim().length
     * 
     * @param string the string to test
     * @return <code>true</code> if the String consist only of white spaces, false otherwise
     */
    public static boolean isWhitespacesOnly(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Joins the objects (using their toString method) using the given separator into one string.
     * 
     * @param objects the objects that should be joined together
     * @param separator the separator to use between the object-string representations
     * @return a string, containing the string representations of all given objects, joined together by the use of the
     *         given separator.
     */
    public static String join(Collection<?> objects, String separator) {
        requireNonNull(objects, "Collection of objects to join must not be null!");
        requireNonNull(separator, "Separator must not be null!");
        StringBuilder builder = new StringBuilder();
        for (Object object : objects) {
            builder.append(object);
            builder.append(separator);
        }
        if (builder.length() >= separator.length()) {
            builder.delete(builder.length() - separator.length(), builder.length());
        }
        return builder.toString();
    }
}
