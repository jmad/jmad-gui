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
