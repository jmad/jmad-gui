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
package cern.accsoft.steering.jmad.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is a static factory for a jmad service.
 * 
 * @author kfuchsbe
 */
public final class JMadServiceFactory {

    private JMadServiceFactory() {
        /* only static methods */
    }

    /**
     * creates a new jmad-service.
     * 
     * @return the new service
     */
    public static JMadService createJMadService() {
        /* creating the application - context. */
        ApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[] { "app-ctx-jmad-service.xml" });
        return (JMadService) appCtx.getBean("jmadService");
    }
}
