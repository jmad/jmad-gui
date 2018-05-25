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
 * Keeps track of all actually selected elements of a model. Each gui element which wants to change the selected
 * elements can do this with the provide methods. Elements which want to get notified on changes can add a listener to
 * this class.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface ElementSelectionManager extends GenericObservableManager<ElementSelectionManagerListener> {

    /**
     * returns all the selected elements in the order as they appear in the sequence. The order is independent of the
     * time when selecting the individual elements.
     * 
     * @return all the actually selected elements
     */
    public List<Element> getSelectedElements();

    /**
     * Returns the element which was last selected.
     * 
     * @return the most recently selected element
     */
    public Element getSelectedElement();

    /**
     * Sets the given elements as the actually selected ones
     * 
     * @param selectedElements all the selected elements (migth be an empty list)
     * @param mostRecentlySelectedElement the element which was most recently selected. might be <code>null</code>
     */
    public void setSelectedElements(List<Element> selectedElements, Element mostRecentlySelectedElement);

}
