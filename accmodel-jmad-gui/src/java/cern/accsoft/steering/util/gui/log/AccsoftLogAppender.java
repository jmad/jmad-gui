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

package cern.accsoft.steering.util.gui.log;

import org.apache.log4j.Level;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.RootLogger;
import org.slf4j.Logger;

import cern.accsoft.gui.frame.MessageManager;
import cern.accsoft.gui.frame.StatusLine;

/**
 * This class is a simple implementation of a log4j - appender, that forwards the log-calls to the accsoft log-panel and
 * statusline, without having to implement the LogSource interface for all classes, that want to use logging. So one
 * simply can use the log4j-api in all classes.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class AccsoftLogAppender extends WriterAppender {

    /* the log-panel of the main frame. There we will send all messages to. */
    private Logger logger;

    /* The statusline of the mainfram. There we will send some messages to. */
    private StatusLine statusLine;

    /*
     * the minimum Level which to display. All events with higher level are displayed. Per default set to info
     */
    private Level minLevel = Level.INFO;

    /**
     * the constructor, which allows to define the logger and statusline
     * 
     * @param logger
     * @param statusLine
     */
    public AccsoftLogAppender(Logger logger, StatusLine statusLine) {
        this.logger = logger;
        this.statusLine = statusLine;
        /*
         * add ourself to Log4j
         */
        RootLogger.getRootLogger().addAppender(this);
    }

    /**
     * the constructor. This appender adds himself to the Log4J - system.
     */
    public AccsoftLogAppender() {
        this(MessageManager.getConsoleLogger(), MessageManager.getStatusLine());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.log4j.WriterAppender#append(org.apache.log4j.spi.LoggingEvent)
     */
    @Override
    public void append(LoggingEvent event) {
        appendToLogger(event);
        appendToStatusLine(event);
    }

    /**
     * decides if to append the event to the logger
     * 
     * @param event the event to append
     */
    private void appendToLogger(LoggingEvent event) {
        Level level = event.getLevel();
        if ((minLevel != null) && (minLevel.toInt() > level.toInt())) {
            return;
        }

        String message = formatLoggerMessage(event);
        if (Level.DEBUG.equals(level)) {
            logger.debug(message);
        } else if (Level.INFO.equals(level)) {
            logger.info(message);
        } else if (Level.WARN.equals(level)) {
            logger.warn(message);
        } else if (Level.ERROR.equals(level)) {
            logger.error(message);
        } else if (Level.FATAL.equals(level)) {
            logger.error(message);
        }
    }

    /**
     * decides if to append the event to the statusline and what level.
     * 
     * @param event the event to append.
     */
    private void appendToStatusLine(LoggingEvent event) {
        Level level = event.getLevel();
        String message = formatStatusLineMessage(event);

        if ((Level.ERROR.equals(level)) || (Level.FATAL.equals(level))) {
            this.statusLine.error(message);
        } else if (Level.WARN.equals(level)) {
            this.statusLine.warn(message);
        } else if (Level.INFO.equals(level)) {
            this.statusLine.info(message);
        }
    }

    /**
     * composes a suitable message for the Logging-frame.
     * 
     * @param event the LoggingEvent
     * @return the message
     */
    private String formatLoggerMessage(LoggingEvent event) {
        String message = formatStatusLineMessage(event);
        if (event.getThrowableInformation() != null) {
            Throwable throwable = event.getThrowableInformation().getThrowable();
            while (throwable != null) {
                message += "\n  caused by : " + throwable.getMessage();
                throwable = throwable.getCause();
            }
        }
        return message;
    }

    /**
     * composes a message suitable for the StatusLine
     * 
     * @param event the LoggingEvent
     * @return the message
     */
    private String formatStatusLineMessage(LoggingEvent event) {
        return event.getMessage().toString();
    }

    /**
     * @return the maxLevel
     */
    public final Level getMinLevel() {
        return minLevel;
    }

    /**
     * @param minLevel the maxLevel to set
     */
    public final void setMinLevel(Level minLevel) {
        this.minLevel = minLevel;
    }

}
