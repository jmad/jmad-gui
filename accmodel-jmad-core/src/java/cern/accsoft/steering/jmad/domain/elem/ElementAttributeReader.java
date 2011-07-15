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
