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
package cern.accsoft.steering.jmad.domain.knob.attribute;

import cern.accsoft.steering.jmad.JMadConstants;
import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.domain.knob.AbstractKnob;
import cern.accsoft.steering.jmad.domain.knob.KnobType;
import cern.accsoft.steering.jmad.domain.knob.MadxParameter;

/**
 * this knob is a wrapper of the knob interface to element-attributes.
 * 
 * @author kfuchsbe
 */
public class ElementAttribute extends AbstractKnob implements MadxParameter {

    /** a reference to an element. */
    private final Element element;

    /** the name of the attribute */
    private final String attributeName;

    /**
     * The constructor which requires both, an element and the attribute name
     * 
     * @param element the element instance to which the attribute belongs
     * @param attributeName the name of the attribute
     */
    public ElementAttribute(Element element, String attributeName) {
        this.element = element;
        this.attributeName = attributeName;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getName() {
        return getKey();
    }

    @Override
    protected final void doSetTotalValue(double value) {
        element.setAttribute(attributeName, value);
    }

    @Override
    public double getTotalValue() {
        return element.getAttribute(attributeName);
    }

    @Override
    public String getKey() {
        return createKey(this.element, this.attributeName);
    }

    @Override
    public KnobType getType() {
        return getKnobType();
    }

    public static final KnobType getKnobType() {
        return KnobType.ELEMENT_ATTRIBUTE;
    }

    /**
     * creates a key for an given element and attribute-name
     * 
     * @param element the element to use in the combined key
     * @param attributeName the name of the attribute to use in the combined key
     * @return a key which can be used in maps
     */
    public static final String createKey(Element element, String attributeName) {
        return createMadxName(element, attributeName);
    }

    /**
     * creates the name which correctly represents this attribute in madx
     * 
     * @param element the element to which the attribute belongs
     * @param attributeName the name of the attribute
     * @return a name representing this attribute in MadX
     */
    private static final String createMadxName(Element element, String attributeName) {
        return element.getName().toUpperCase() + Element.ATTR_SEPARATOR
                + attributeName.toLowerCase(JMadConstants.DEFAULT_LOCALE);
    }

    /**
     * extracts the element-name from the key
     * 
     * @param key the key used in maps for element-attributes
     * @return the name of the element which is contained in the attribute-key
     */
    public static final String getElementNameFromKey(String key) {
        int index = key.indexOf(Element.ATTR_SEPARATOR);
        if (index >= 0) {
            return key.substring(0, index);
        }
        return null;
    }

    /**
     * extracts the attribute-name from the key
     * 
     * @param key the key from which to extract the attribute
     * @return the name of the attribute contained in the key
     */
    public static final String getAttributeNameFromKey(String key) {
        int index = key.indexOf(Element.ATTR_SEPARATOR);
        if (index >= 0) {
            return key.substring(index + Element.ATTR_SEPARATOR.length());
        }
        return null;
    }

    @Override
    public String getMadxName() {
        return createMadxName(this.element, this.attributeName);
    }

}
