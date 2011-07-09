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
