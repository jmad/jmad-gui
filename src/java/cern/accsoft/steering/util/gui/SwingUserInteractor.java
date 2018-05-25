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

import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cern.accsoft.steering.util.gui.dialog.PanelDialog;
import cern.accsoft.steering.util.gui.panels.PanelProvider;

/**
 * This class provides methods for user-interaction through swing-dialogs.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class SwingUserInteractor implements UserInteractor {

    /** the mainPanel as parent for all dialogs */
    private Frame mainFrame;

    @Override
    public String askForString(String question, String defaultInput) {
        return JOptionPane.showInputDialog(getMainFrame(), question, defaultInput);
    }

    @Override
    public String askForString(String question) {
        return JOptionPane.showInputDialog(getMainFrame(), question);
    }

    @Override
    public boolean showPanelDialog(JPanel panel) {
        return PanelDialog.show(panel, getMainFrame());
    }

    @Override
    public boolean showPanelDialog(PanelProvider panelProvider) {
        return PanelDialog.show(panelProvider, getMainFrame());
    }

    /**
     * @param mainFrame the mainFrame to set
     */
    public void setMainFrame(Frame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * @return the mainFrame
     */
    public Frame getMainFrame() {
        return mainFrame;
    }

}
