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

package cern.accsoft.steering.jmad.gui.manage;

import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;

/**
 * This listener can be attached to an element selection manager and gets notified when the selected elements change.
 * 
 * @author kaifox
 */
public interface ElementSelectionManagerListener {

    /**
     * This method is called, when the selected elements changed
     * 
     * @param selectedElements all the currently selected elements in the order as they appear in the sequence.
     * @param lastSelectedElement The element which was selected mostResently
     */
    public void changedSelectedElements(List<Element> selectedElements, Element lastSelectedElement);

}
