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
package cern.accsoft.steering.jmad.domain.elem.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.elem.MadxElementType;

public final class ElementFactory {
    /** The logger for this class */
    private static final Logger LOGGER = Logger.getLogger(ElementFactory.class);

    private ElementFactory() {
        /* only static methods */
    }

    /**
     * factory method for an element of the according type. This has to be overridden by each enum-instance.
     * 
     * @param madxElementType the type of the element as represented in madx itself
     * @param elementName the name of the new element
     * @return an element of the correct Type.
     */
    public static Element createElement(MadxElementType madxElementType, String elementName) {
        Class<? extends Element> clazz = madxElementType.getElementType().getElementClass();

        Element element = null;
        String errorMessage = "Could not retrieve constructor for element of type '" + clazz
                + "'. Maybe it does not have a constructor with parameters (MadxElementType,String)?";
        Constructor<? extends Element> constructor = null;
        try {
            constructor = clazz.getConstructor(MadxElementType.class, String.class);
        } catch (SecurityException e) {
            LOGGER.error(errorMessage, e);
        } catch (NoSuchMethodException e) {
            LOGGER.error(errorMessage, e);
        }

        if (constructor != null) {
            String message = "Could not create element of type '" + clazz + "'";
            try {
                element = constructor.newInstance(madxElementType, elementName);
            } catch (IllegalArgumentException e) {
                LOGGER.error(message, e);
            } catch (InstantiationException e) {
                LOGGER.error(message, e);
            } catch (IllegalAccessException e) {
                LOGGER.error(message, e);
            } catch (InvocationTargetException e) {
                LOGGER.error(message, e);
            }
        }
        if (element == null) {
            return new UnknownElement(madxElementType, elementName);
        } else {
            return element;
        }
    }
}
