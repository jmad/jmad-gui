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
package cern.accsoft.steering.jmad.util.xml.converters;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * This is a generic converter that can be used by xstream to convert each field of a class into a <arrbname value="..."
 * /> node.
 * 
 * @param <T> the type of object to convert
 */
public class GenericFieldAttributeConverter<T> implements Converter {

    private static final Logger LOGGER = Logger.getLogger(GenericFieldAttributeConverter.class);

    /** the xstream mapper to lookup field-names and converters */
    private final Mapper mapper;

    /** the converter-lookup to lookup converters */
    private final ConverterLookup converterLookup;

    /** The class which can be handled by the converter */
    private final Class<T> clazz;

    /**
     * The constructor needs the xstream-object to get the mapper and the converter-lookup and a class which can then be
     * converted by this mapper.
     * 
     * @param xStream the xstream object
     * @param clazz the class to be treated by this converter
     */
    public GenericFieldAttributeConverter(XStream xStream, Class<T> clazz) {
        this.mapper = xStream.getMapper();
        this.converterLookup = xStream.getConverterLookup();
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext ctx) {

        T valueObject = (T) object;

        /*
         * we use reflection to loop through the fields
         */
        for (Field field : this.clazz.getDeclaredFields()) {
            SingleValueConverter converter = getConverter(field);
            if (converter == null) {
                continue;
            }

            String fieldAlias = mapper.serializedMember(this.clazz, field.getName());

            /*
             * ensure that we can access private fields.
             */
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            Object value = null;
            try {
                value = field.get(valueObject);
            } catch (Exception e) {
                LOGGER.error("Could not get the value from object.");
                continue;
            }

            if (value != null) {
                writer.startNode(fieldAlias);
                writer.addAttribute("value", converter.toString(value));
                writer.endNode();
            }
        }

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {

        T newObject = null;
        try {
            newObject = this.clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("Could not instantiate object of type '" + clazz.getCanonicalName()
                    + "'. Maybe it does not have a public default constructor?");
            return null;
        }

        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            String fieldName = mapper.realMember(this.clazz, nodeName);

            /*
             * the node name is the field name. So we try to lookup the field.
             */
            Field field;
            try {
                field = this.clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                LOGGER.error("No field of name '" + fieldName + "' found in class '" + this.clazz.getCanonicalName()
                        + "'. Cannot load the value.", e);
                reader.moveUp();
                continue;
            }

            /*
             * then we see if we can get a converter. If we dont get one we have to skip the field.
             */
            SingleValueConverter converter = getConverter(field);
            if (converter == null) {
                reader.moveUp();
                continue;
            }

            /*
             * ensure that privet fields are accessible
             */
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            Object value = converter.fromString(reader.getAttribute("value"));
            try {
                field.set(newObject, value);
            } catch (Exception e) {
                LOGGER.error("Could not set the value to objec.", e);
            }

            reader.moveUp();
        }
        return newObject;
    }

    private SingleValueConverter getConverter(Field field) {
        Class<?> fieldType = field.getType();
        String fieldName = field.getName();

        /*
         * first we see if we can get a converter from the mapper. To make this work, all the fields to be converted
         * have to be annotated by the
         * 
         * @XStreamAsAttribute annotation.
         */

        /*
         * TODO: To work for xstream version 1.3.1 this has to be changed to:
         * 
         * SingleValueConverter singleValueConverter = mapper.getConverterFromAttribute(this.clazz, fieldName,
         * fieldType);
         */
        SingleValueConverter singleValueConverter = mapper.getConverterFromAttribute(this.clazz, fieldName);

        /*
         * if this did not succeed then we try to lookup the converter from the ConverterLookup ... but this is then not
         * necessarily a SingleValueConverter ...
         */
        if (singleValueConverter == null) {
            Converter converter = converterLookup.lookupConverterForType(fieldType);
            if (converter instanceof SingleValueConverter) {
                singleValueConverter = (SingleValueConverter) converter;
            }
        }

        /*
         * if still no success then we have to skip the field
         */
        if (singleValueConverter == null) {
            LOGGER.error("Could not find single-value converter for type '" + fieldType.getCanonicalName() + "'");
        }
        return singleValueConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean canConvert(Class otherClazz) {
        return otherClazz.equals(this.clazz);
    }
}
