package cern.accsoft.steering.jmad.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void testIsWhiteSpace() {
        assertTrue(StringUtil.isWhitespacesOnly(""));
        assertTrue(StringUtil.isWhitespacesOnly(" "));
        assertTrue(StringUtil.isWhitespacesOnly(" \n "));
        assertTrue(StringUtil.isWhitespacesOnly("\r\n"));

        assertFalse(StringUtil.isWhitespacesOnly("a"));
        assertFalse(StringUtil.isWhitespacesOnly("1"));
    }
}
