// @formatter:off
/*******************************************************************************
 * This file is part of JMad. Copyright (c) 2008-2011, CERN. All rights reserved. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.util.gui.log;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.RootLogger;

import java.io.StringWriter;

/**
 * This is singleton, which appends itself to the Log4j system and provides methods to readout the logged stream.
 * <p>
 * simplest usage: <blockquote> <code>
 * Log4JSniffer.INSTANCE.setRecording(true);
 * </code> ... then log some messages and in the end retrieve the string by calling:
 * <p>
 * <code>
 * String result = Log4JSniffer.INSTANCE.toString();
 * </code> and stop the recording, if not needed:
 * <p>
 * <code>
 * Log4JSniffer.INSTANCE.setRecording(true);
 * </code> </blockquote>
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public enum Log4JSniffer {
    INSTANCE;

    private Log4JSniffer() {
        reset();
        /*
         * add ourself to Log4j
         */
        RootLogger.getRootLogger().addAppender(this.stringBufferAppender);
    }

    /** the log-appender. */
    private StringBufferLogAppender stringBufferAppender = new StringBufferLogAppender();

    /**
     * the minimum Level which to display. All events with higher level are displayed. Per default set to info
     */
    private Level minLevel = Level.INFO;

    /** The buffer in which we collect the messages */
    private StringWriter stringWriter;

    /**
     * <code>true</code> if log messages should be appended to the string buffer, <code>false</code> if not.
     */
    private boolean recording = false;

    /**
     * @return the minLevel
     */
    public final Level getMinLevel() {
        return minLevel;
    }

    /**
     * resets the writer by removing the old string-buffer and adding a new one.
     */
    public final synchronized void reset() {
        this.stringWriter = new StringWriter();
        this.stringBufferAppender.setWriter(this.stringWriter);
    }

    /**
     * returns the actual content of the String-buffer.
     */
    public final synchronized String toString() {
        this.stringWriter.flush();
        return this.stringWriter.toString();
    }

    /**
     * @param minLevel the maxLevel to set
     */
    public final synchronized void setMinLevel(Level minLevel) {
        this.minLevel = minLevel;
    }

    public final synchronized void setRecording(boolean recording) {
        this.recording = recording;
    }

    public boolean isRecording() {
        return recording;
    }

    /**
     * getter for the actual Log4J writer appender.
     * <p>
     * This can be used e.g. to set different layouts.
     * 
     * @return the instance of {@link StringBufferLogAppender};
     */
    public Appender getAppender() {
        return stringBufferAppender;
    }

    /**
     * This class is a simple implementation of a log4j - appender that collects all the log events into a string
     * buffer. The string can then be fetched for further use.
     * 
     * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
     */
    private class StringBufferLogAppender extends WriterAppender {

        /**
         * the default constructor. it appends itself to the log4j system
         */
        public StringBufferLogAppender() {
            super();
            super.setLayout(new SimpleLayout());
        }

        @Override
        public void append(LoggingEvent event) {
            if (!isRecording()) {
                return;
            }
            if ((minLevel != null) && (minLevel.toInt() > event.getLevel().toInt())) {
                return;
            }
            super.append(event);
        }
    }
}
