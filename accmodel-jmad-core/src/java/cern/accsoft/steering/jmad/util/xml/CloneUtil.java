// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
package cern.accsoft.steering.jmad.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

/**
 * This is a utility class that uses the XStream mechanism to clone objects. Custom converters can be provided in order
 * to control behaviour of the cloning.
 * 
 * @author kfuchsbe
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
