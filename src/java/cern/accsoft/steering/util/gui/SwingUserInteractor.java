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

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cern.accsoft.steering.util.gui.dialog.PanelDialog;
import cern.accsoft.steering.util.gui.panels.PanelProvider;

/**
 * This class provides methods for user-interaction through swing-dialogs.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class SwingUserInteractor implements UserInteractor {

    @Override
    public String askForString(String question, String defaultInput) {
        return askForString(question, defaultInput, null);
    }

    public String askForString(String question, String defaultInput, Component parent) {
        return JOptionPane.showInputDialog(parent, question, defaultInput);
    }

    @Override
    public String askForString(String question) {
        return askForString(question, null, null);
    }

    @Override
    public boolean showPanelDialog(JPanel panel, Component parent) {
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, parent);
        return PanelDialog.show(panel, frame);
    }

    @Override
    public boolean showPanelDialog(PanelProvider panelProvider, Component parent) {
        JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, parent);
        return PanelDialog.show(panelProvider, frame);
    }

}
