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
package cern.accsoft.steering.jmad.domain.elem;

import java.util.Collection;

import cern.accsoft.steering.jmad.domain.ex.JMadModelException;

/**
 * This interface provides a method to read attribute properties from MadX.
 * 
 * @author kaifox
 */
public interface ElementAttributeReader {

    /**
     * reads the properties for the given element
     * 
     * @param element the element for which to read the properties
     * @throws JMadModelException if the reading of the attributes fails
     */
    public void readAttributes(Element element) throws JMadModelException;

    /**
     * reads the properties for all the given elements
     * 
     * @param elements the elements for which to read the attributes
     * @throws JMadModelException if the reading of the attributes failed
     */
    public void readAttributes(Collection<Element> elements) throws JMadModelException;

}
