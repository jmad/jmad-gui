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

package cern.accsoft.steering.jmad.gui.menu;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

import cern.accsoft.steering.jmad.gui.actions.JMadGuiActions;

/**
 * This class creates the actual instances of the toolbar and the menuBar for JMad.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadMenuFactoryImpl implements JMadMenuFactory {

    private JMadGuiActions jmadGuiActions;

    @Override
    public JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(jmadGuiActions.getCreateModelFromRepositoryAction());
        toolBar.add(jmadGuiActions.getCreateModelFromUriAction());
        toolBar.add(jmadGuiActions.getCreateModelFromFileAction());
        toolBar.addSeparator();
        toolBar.add(jmadGuiActions.getExportModelUriAction());
        toolBar.add(jmadGuiActions.getExportModelAction());
        toolBar.add(jmadGuiActions.getCloseActiveModelAction());
        toolBar.addSeparator();
        toolBar.add(jmadGuiActions.getChooseRangeAction());
        toolBar.add(jmadGuiActions.getChooseOpticsAction());
        toolBar.addSeparator();
        toolBar.add(jmadGuiActions.getExitAction());
        toolBar.setFloatable(false);
        return toolBar;
    }

    @Override
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        fileMenu.add(jmadGuiActions.getCreateModelFromRepositoryAction());
        fileMenu.add(jmadGuiActions.getCreateModelFromUriAction());
        fileMenu.add(jmadGuiActions.getCreateModelFromFileAction());
        fileMenu.addSeparator();
        fileMenu.add(jmadGuiActions.getExportModelUriAction());
        fileMenu.add(jmadGuiActions.getExportModelAction());
        fileMenu.add(jmadGuiActions.getCloseActiveModelAction());
        fileMenu.addSeparator();
        fileMenu.add(jmadGuiActions.getExitAction());

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(jmadGuiActions.getShowAboutBoxAction());
        menuBar.add(helpMenu);

        return menuBar;
    }

    @Override
    public List<Action> getToolBarActions() {
        /* Original implementation returned empty here.. */
        return Collections.emptyList();
    }

    public void setJmadGuiActions(JMadGuiActions jmadGuiActions) {
        this.jmadGuiActions = jmadGuiActions;
    }
}
