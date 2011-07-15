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

package cern.accsoft.steering.jmad.gui.log;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

import cern.accsoft.steering.util.gui.log.Log4JSniffer;

public class Log4JSnifferTest {

    /** the system dependent line separator */
    private static final String LINESEP = System.getProperty("line.separator");

    /** The logger which we use for testing the sniffer */
    private final static Logger logger = Logger.getLogger(Log4JSnifferTest.class);

    @Test
    public void testLog4JSniffing() {
        /* start the recording by the sniffer. */
        Log4JSniffer.INSTANCE.setRecording(true);

        /*
         * log a few messages and retrieve the result.
         */
        logTestMessages();
        String result = Log4JSniffer.INSTANCE.toString();
        System.out.println("Sniffer returned:\n" + result);
        assertEquals("Only info, warn and error messge, because default minLevel=INFO", "ERROR - error message"
                + LINESEP + "WARN - warn message" + LINESEP + "INFO - info message" + LINESEP + "", result);

        /*
         * reset the sniffer
         */
        Log4JSniffer.INSTANCE.reset();
        result = Log4JSniffer.INSTANCE.toString();
        System.out.println("Sniffer returned:\n" + result);
        assertEquals("should return empty string", "", result);

        /*
         * change the log level to DEBUG and we should get more messages
         */
        Log4JSniffer.INSTANCE.setMinLevel(Level.DEBUG);
        logTestMessages();
        result = Log4JSniffer.INSTANCE.toString();
        System.out.println("Sniffer returned:\n" + result);
        assertEquals("Only debug to error messages, because minLevel=DEBUG", "ERROR - error message" + LINESEP
                + "WARN - warn message" + LINESEP + "INFO - info message" + LINESEP + "DEBUG - debug message" + LINESEP
                + "", result);

        /*
         * test the recording on/off option
         */
        Log4JSniffer.INSTANCE.reset();

        /* switch off recording */
        Log4JSniffer.INSTANCE.setRecording(false);
        logTestMessages();
        result = Log4JSniffer.INSTANCE.toString();
        System.out.println("Sniffer returned:\n" + result);
        assertEquals("should return empty string because recording was set to false", "", result);

        /* switch back on */
        Log4JSniffer.INSTANCE.setRecording(true);
        logTestMessages();
        result = Log4JSniffer.INSTANCE.toString();
        System.out.println("Sniffer returned:\n" + result);

        assertEquals("Only debug to error messages, because minLevel=DEBUG", "ERROR - error message" + LINESEP
                + "WARN - warn message" + LINESEP + "INFO - info message" + LINESEP + "DEBUG - debug message" + LINESEP
                + "", result);
    }

    @Test
    public void testChangeLayout() {
        /*
         * change the layout of the instance
         */
        Log4JSniffer.INSTANCE.reset();
        Log4JSniffer.INSTANCE.setMinLevel(Level.ERROR);
        Log4JSniffer.INSTANCE.getAppender().setLayout(new PatternLayout("%p:%c{1}:%m" + LINESEP + ""));
        logTestMessages();
        String result = Log4JSniffer.INSTANCE.toString();
        System.out.println("Sniffer returned:n" + result);
        assertEquals("Only error message, because minLevel=ERROR", "ERROR:Log4JSnifferTest:error message" + LINESEP
                + "", result);

        /* an other example, but hard to assert because of time ;-) */
        Log4JSniffer.INSTANCE.reset();
        Log4JSniffer.INSTANCE.getAppender().setLayout(new PatternLayout("%p:%d{ISO8601}:%c{1}:%m" + LINESEP + ""));
        logTestMessages();
        result = Log4JSniffer.INSTANCE.toString();
        System.out.println("Sniffer returned:" + LINESEP + "" + result);
    }

    private void logTestMessages() {
        /*
         * one message of each level
         */
        logger.error("error message");
        logger.warn("warn message");
        logger.info("info message");
        logger.debug("debug message");
        logger.trace("trace message");
    }

}
