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

package cern.accsoft.steering.util.gui;

import javax.swing.JPanel;

import cern.accsoft.steering.util.gui.panels.PanelProvider;

import java.awt.Component;

/**
 * this interface provides some simple methods for user-interaction.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public interface UserInteractor {

    /**
     * @param question the the String, which describes to the user, what to enter.
     * @return the answer. Null, if aborted
     */
    String askForString(String question);

    /**
     * @param question the the String, which describes to the user, what to enter.
     * @param defaultInput the string to display as default for the input
     * @return the answer. Null, if aborted
     */
    String askForString(String question, String defaultInput);

    /**
     * opens a dialog with the given panel
     * 
     * @param panel the panel to show in the dialog
     * @param parent the parent of the dialog
     * @return true, if the ok-button was pressed, false if not
     */
    boolean showPanelDialog(JPanel panel, Component parent);

    /**
     * opens a dialog with the panel of the given panelProvider
     *
     * @param panelProvider the panelProvider which provides the panel to show
     * @param parent the parent of the dialog
     * @return true, if the ok button was pressed, false otherwise
     */
    boolean showPanelDialog(PanelProvider panelProvider, Component parent);
}
