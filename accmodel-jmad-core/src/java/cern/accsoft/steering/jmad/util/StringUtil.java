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
/**
 * 
 */
package cern.accsoft.steering.jmad.util;

/**
 * some utils to handle strings
 * 
 * @author kaifox
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
}
