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

package cern.accsoft.steering.jmad.gui.manage.impl;

import java.util.ArrayList;
import java.util.List;

import cern.accsoft.steering.jmad.domain.elem.Element;
import cern.accsoft.steering.jmad.gui.manage.AbstractObservableManager;
import cern.accsoft.steering.jmad.gui.manage.ElementSelectionManager;
import cern.accsoft.steering.jmad.gui.manage.ElementSelectionManagerListener;

/**
 * default implementation of the {@link ElementSelectionManager}. It keepos track of all the elements that are currently
 * selected.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class ElementSelectionManagerImpl extends AbstractObservableManager<ElementSelectionManagerListener> implements
        ElementSelectionManager {

    /** All the selected elements */
    private List<Element> selectedElements = new ArrayList<Element>();

    /** The most recently selected element */
    private Element selectedElement = null;

    @Override
    public Element getSelectedElement() {
        return this.selectedElement;
    }

    @Override
    public List<Element> getSelectedElements() {
        return this.selectedElements;
    }

    /**
     * notifies all the listeners that the selected elements have changed.
     */
    private void notifiyListenersChangedSelectedElements() {
        for (ElementSelectionManagerListener listener : getListeners()) {
            listener.changedSelectedElements(this.selectedElements, this.selectedElement);
        }
    }

    @Override
    public void setSelectedElements(List<Element> selectedElements, Element selectedElement) {
        this.selectedElements = selectedElements;
        this.selectedElement = selectedElement;
        notifiyListenersChangedSelectedElements();
    }
}
