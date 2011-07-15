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
