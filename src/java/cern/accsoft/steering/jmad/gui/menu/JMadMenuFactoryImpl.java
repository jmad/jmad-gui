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

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import cern.accsoft.gui.beans.AboutBox;
import cern.accsoft.gui.frame.FrameManager;
import cern.accsoft.steering.jmad.gui.icons.Icon;
import cern.accsoft.steering.jmad.gui.manage.ChooseActionFactory;

/**
 * This class creates the actual instances of the toolbar and the menuBar for JMad.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class JMadMenuFactoryImpl implements JMadMenuFactory {

    /** The action-factory */
    private ChooseActionFactory chooseActionFactory;

    /**
     * The action to show the About-box
     */
    private Action showAboutBoxAction = new AbstractAction("About") {
        private static final long serialVersionUID = -1695054883852564439L;

        @Override
        public void actionPerformed(ActionEvent evt) {
            AboutBox aboutBox = new AboutBox(FrameManager.getInstance().getMainFrame().getJFrame());
            aboutBox.setIcon(Icon.SPLASH.getImageIcon());
            aboutBox.setText("JMad GUI", "cern-accsoft-steering-jmad-gui",
                    "(C) Copyright CERN 2008-2010  Kajetan Fuchsberger AB-OP-SPS", null);
            aboutBox.setVisible(true);
        }
    };

    @Override
    public JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(getChooseActionFactory().getNewModelAction());
        toolBar.add(getChooseActionFactory().getCloseActiveModelAction());
        toolBar.addSeparator();
        toolBar.add(getChooseActionFactory().getImportAction());
        toolBar.add(getChooseActionFactory().getExportAction());
        toolBar.addSeparator();
        toolBar.add(getChooseActionFactory().getChooseRangeAction());
        toolBar.add(getChooseActionFactory().getChooseOpticsAction());
        toolBar.addSeparator();
        toolBar.add(getChooseActionFactory().getExitAction());
        return toolBar;
    }

    /**
     * creates the whole menu bar for the jmad-gui.
     * 
     * @return the new menuBar
     */
    @Override
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        fileMenu.add(getChooseActionFactory().getNewModelAction());
        fileMenu.add(getChooseActionFactory().getCloseActiveModelAction());
        fileMenu.addSeparator();
        fileMenu.add(getChooseActionFactory().getImportAction());
        fileMenu.add(getChooseActionFactory().getExportAction());
        fileMenu.addSeparator();
        fileMenu.add(getChooseActionFactory().getExitAction());

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(this.showAboutBoxAction);
        menuBar.add(helpMenu);

        return menuBar;
    }

    @Override
    public List<Action> getToolBarActions() {
        List<Action> actions = new ArrayList<Action>();

        return actions;
    }

    public void setChooseActionFactory(ChooseActionFactory chooseActionFactory) {
        this.chooseActionFactory = chooseActionFactory;
    }

    private ChooseActionFactory getChooseActionFactory() {
        return chooseActionFactory;
    }

}
