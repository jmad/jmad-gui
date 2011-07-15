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

package cern.accsoft.steering.jmad.domain.elem;

import java.util.List;

public interface Element {
    public static final String ATTR_SEPARATOR = "->";

    public String getName();

    public Position getPosition();

    public void setPosition(double position);

    public void setPosition(Position position);

    public void setAttribute(String name, Double value);

    /**
     * Retrieves the value of the attribute identified by the given name.
     * 
     * @param attributeName the name of the attribute to retrieve
     * @return the value of the attribute of the given name. If the attribute is not available for the element, then
     *         <code>null</code>.
     */
    public Double getAttribute(String attributeName);

    public List<String> getAttributeNames();

    /**
     * @return the exact element type as defined in the madx sequence of this element as enum
     */
    public MadxElementType getMadxElementType();

    public void setAttributesInitialized(boolean attributesInitialized);

    public void setListenersEnabled(boolean listenersEnabled);

    public void addListener(ElementListener listener);

    public void removeListener(ElementListener listener);

}
