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
package cern.accsoft.steering.jmad.service;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * 
 */

/**
 * creates a JMadService and publish it via py4j. This way it will be possible by any python program to use it.
 * 
 * @author kaifox
 */
public class PyJMadServer {

    /** The logger for the class */
    private static final Logger LOGGER = Logger.getLogger(PyJMadServer.class);

    /**
     * @param args
     */
    public void main(String[] args) {

        /* configure the log4j - system */
        BasicConfigurator.configure();

        @SuppressWarnings("unused")
        JMadService jMadService = JMadServiceFactory.createJMadService();

        LOGGER.info("PyJMadServer started.");
    }

}
