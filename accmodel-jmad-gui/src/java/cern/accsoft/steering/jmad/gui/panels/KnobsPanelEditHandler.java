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

import cern.accsoft.steering.jmad.domain.knob.Knob;

/**
 * provides methods to handle the selection of certain strengthes in a {@link AbstractKnobsPanel}
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface KnobsPanelEditHandler extends TablePanelEditHandler {

    /**
     * @param value
     * @return true, if the strength is selected, false, if not
     */
    public boolean getSelectionValue(Knob value);

    /**
     * sets the strength as selected or unselected, accordiog to the given value
     * 
     * @param modelValue the strength for which to modify the selection - value
     * @param value true: select the strength, false: deselect
     */
    public void setSelectionValue(Knob modelValue, boolean value);
}
