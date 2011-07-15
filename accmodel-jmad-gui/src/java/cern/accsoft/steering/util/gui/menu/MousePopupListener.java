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

package cern.accsoft.steering.util.gui.menu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * extension for {@link MouseAdapter} to check if a popup menu shall show up.
 * 
 * @author Kajetan Fuchsberger (kajetan.fuchsberger at cern.ch)
 */
public class MousePopupListener extends MouseAdapter {

    private JPopupMenu popupMenu;
    private Component invoker;

    /**
     * the constructor
     * 
     * @param invoker the parent component
     * @param popupMenu the popupMenu that shall show up, when the mouse is clicked
     */
    public MousePopupListener(Component invoker, JPopupMenu popupMenu) {
        this.invoker = invoker;
        this.invoker.addMouseListener(this);
        this.popupMenu = popupMenu;
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        super.mousePressed(evt);
        checkPopup(evt);
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        super.mouseReleased(evt);
        checkPopup(evt);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        super.mouseClicked(evt);
        checkPopup(evt);
    }

    private void checkPopup(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            popupMenu.show(invoker, evt.getX(), evt.getY());
        }
    }
}