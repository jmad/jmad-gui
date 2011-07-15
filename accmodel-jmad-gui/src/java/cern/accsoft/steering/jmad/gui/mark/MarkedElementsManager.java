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

package cern.accsoft.steering.jmad.gui.mark;

import java.util.Collection;

/**
 * A manager for markers in Charts
 * 
 * @author kfuchsbe
 */
public interface MarkedElementsManager {

    /**
     * adds an the given elementName to the marked elements
     * 
     * @param marker
     */
    public void addElementName(String elementName);

    /**
     * removes a elementName from the list of marked elements
     * 
     * @param elementName the name of the element to remove
     */
    public void removeElementName(String elementName);

    /**
     * @param elementName
     * @return true, if the manager contains the given name, false otherwise
     */
    public boolean contains(String elementName);

    /**
     * @return all contained elementNames
     */
    public Collection<String> getElementNames();

    /**
     * @param listener the listener to add
     */
    public void addListener(MarkedElementsManagerListener listener);

    /**
     * @param listener the listener to remove
     */
    public void removeListener(MarkedElementsManagerListener listener);
}
