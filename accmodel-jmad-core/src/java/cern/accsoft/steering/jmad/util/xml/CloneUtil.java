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

/**
 * 
 */
package cern.accsoft.steering.jmad.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

/**
 * This is a utility class that uses the XStream mechanism to clone objects. Custom converters can be provided in order
 * to control behaviour of the cloning.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public final class CloneUtil {

    private CloneUtil() {
        /* Only static methods */
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(Class<T> clazz, T object) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.processAnnotations(clazz);
        return (T) xStream.fromXML(xStream.toXML(object));
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(Class<T> clazz, T object, Converter converter) {
        XStream xStream = new XStream();
        xStream.registerConverter(converter);
        xStream.autodetectAnnotations(true);
        xStream.processAnnotations(clazz);
        return (T) xStream.fromXML(xStream.toXML(object));

    }

}
