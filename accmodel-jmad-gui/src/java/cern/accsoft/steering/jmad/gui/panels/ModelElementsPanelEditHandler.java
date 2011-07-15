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

package cern.accsoft.steering.jmad.gui.panels;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * defines methods which are called by ModelElementsPanel for using an additional - selection column.
 * 
 * @author kfuchsbe
 */
public interface ModelElementsPanelEditHandler extends TablePanelEditHandler {

    /**
     * @param element
     * @param attributeName
     * @return true if the given attribute of the given element is selected.
     */
    public Boolean getSelectionValue(Element element, String attributeName);

    /**
     * sets the attribute of the element as selected / deselected
     * 
     * @param element the element, for which to set the attribute as selected
     * @param attributeName the attributName to select
     * @param value true for select, false for deselect
     */
    public void setSelectionValue(Element element, String attributeName, boolean value);
}
